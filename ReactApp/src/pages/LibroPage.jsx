import React, {useEffect, useState} from "react";
import {
    Table,
    Button,
    Modal,
    ModalHeader,
    ModalBody,
    FormGroup,
    ModalFooter,
} from "reactstrap";
import axios from "../axios.js";
import instance from "../axios.js";
import NavBar from "../components/NavBar.jsx";
import {AbsoluteCenter, Center, VStack} from "@chakra-ui/react";
import '../App.css';



function LibroPage() {
    const [libros, setLibros] = useState([]);
    const [modalInsertar, setModalInsertar] = useState(false);
    const [modalEdit, setModalEdit] = useState(false);
    const [editingLibro, setEditingLibro] = useState();
    const [id, setId] = useState();
    const [previousLibro, setPreviousLibro] = useState()

    useEffect(() => {
        queryLibros()
    }, []);

    const queryLibros = () => {
        try {
            axios.get(instance.getUri() + "/libros")
                .then(res => {
                        setLibros(res.data)
                    }
                )
        } catch (e) {
            alert("Can't connect with backend")
            console.log(e)
        }
    }

    const onCreateSubmit = (e) => {
        e.preventDefault()

        try {
            axios.post(instance.getUri() + "/libros", {
                'titulo': e.target.titulo.value,
                'fechaPublicacion': e.target.fechaPublicacion.value,
                'autorNombre': e.target.autorNombre.value
            }).then(res => {
                if (res.status === 200) {
                    queryLibros()
                    handleModalInsertHide()
                } else {
                    alert("'Libro' couldn't be created, maybe Autor doesn't exist")
                }
            })
        } catch (e) {
            alert("Can't connect with backend")
            console.log(e)
        }

    }

    const onCreateButton = (e) => {
        document.getElementById("createSubmitBtn").click()
    }

    const handleModalInsertShow = () => {
        setModalInsertar(true);
    }

    const handleModalInsertHide = () => {
        setModalInsertar(false);
    }

    const handleModalEditShow = (libro) => {
        setEditingLibro(libro);
        setPreviousLibro(libro)
        setModalEdit(true);
    }

    const handleModalEditHide = () => {
        setModalEdit(false);
    }

    const deleteLibro = async (libro) => {
        let opt = window.confirm("¿Está seguro que desea eliminar el libro?")
        await getIdFromName(libro)

        if (opt) {
            try {
                axios.delete(instance.getUri() + "/libros" + "/" + id)
                    .then(res => {
                            if (res.status === 204) {
                                queryLibros()
                            } else {
                                alert("'Libro' couldn't be deleted")
                            }
                        }
                    )
            } catch (e) {
                alert("Can't connect with backend")
                console.log(e)
            }
        }
    }

    const getIdFromName = async (libro) => {
        try {
            console.log("a intentar obtener id del titulo")
            await axios.get(instance.getUri() + "/libros" + "/id/" + libro.titulo)
                .then(res => {
                        if (res.status === 200) {
                            console.log("llego, lo seteo")
                            setId(res.data)
                        } else {
                            alert("'Libro' couldn't be edited")
                        }
                    }
                )
        } catch (e) {
            alert("Can't connect with backend")
            console.log(e)
        }
    }

    const onEditSubmit = async (e) => {
        e.preventDefault()
        await getIdFromName(previousLibro)

        try {
            axios.put(instance.getUri() + "/libros" + "/" + id, {
                'titulo': e.target.tituloEdit.value,
                'fechaPublicacion': e.target.fechaPublicacionEdit.value,
                'autorNombre': e.target.autorNombreEdit.value
            })
                .then(res => {
                    console.log(res.status)
                        if (res.status === 200) {
                            queryLibros()
                            handleModalEditHide()
                            flag = true;
                        } else {
                            alert("'Libro' couldn't be edited, maybe Autor doesn't exist")
                        }
                    }
                )
        } catch (e) {
            alert("Can't connect with backend")
            console.log(e)
        }
    }

    const onEditButton = () => {
        document.getElementById("editSubmitBtn").click()
    }

    const handleTituloChange = (e) => {
        setEditingLibro({...editingLibro, titulo: e.target.value})
    }

    const handleFechaPublicacionChange = (e) => {
        setEditingLibro({...editingLibro, fechaPubliacion: e.target.value})
    }

    const handleAutorIdChange = (e) => {
        setEditingLibro({...editingLibro, autorNombre: e.target.value})
    }
    const backgroundStyle = {
        backgroundImage: "url('b2.jpg')",
        backgroundPosition: 'center',
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
        height: '100vh',
        width: '100vw',
        color: 'white'
    };

    return (
        <NavBar>
            <Center style={backgroundStyle}>
                <VStack width={"80%"} alignContent={"center"} alignItems="center" >
                    <h1 style={{marginTop: "1px"}}>Libros</h1>
                    <Button color="success" onClick={handleModalInsertShow} style={{alignSelf: "flex-end"}}>
                        Agregar Libro
                    </Button>
                    <br/><br/>
                    <Table>
                        <thead>
                        <tr>
                            <th>Título</th>
                            <th>Fecha de Publicación</th>
                            <th>Nombre del Autor</th>
                            <th>Acciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        {libros.length > 0 ? libros.map((libro) => (
                                <tr key={libro.autorNombre}>
                                    <td>{libro.titulo}</td>
                                    <td>{libro.fechaPublicacion}</td>
                                    <td>{libro.autorNombre}</td>
                                    <td>
                                        <Button color="primary"
                                                onClick={() => handleModalEditShow(libro)}>Editar</Button>{" "}
                                        <Button color="danger" onClick={() => deleteLibro(libro)}>Eliminar</Button>
                                    </td>
                                </tr>
                            )) :
                            <tr>
                            </tr>
                        }
                        </tbody>
                    </Table>
                    <Modal isOpen={modalInsertar}>
                        <ModalHeader>
                            <div><h3>Agregar Libro</h3></div>
                        </ModalHeader>

                        <ModalBody>
                            <form onSubmit={onCreateSubmit}>
                                <FormGroup>
                                    <label>Título:</label>
                                    <input
                                        style={{marginLeft: '10px'}}
                                        className={"input"}
                                        type="text"
                                        name="titulo"
                                    />
                                </FormGroup>
                                <FormGroup>
                                    <label>Fecha de Publicación:</label>
                                    <input
                                        style={{marginLeft: '10px'}}
                                        className={"input"}
                                        type="date"
                                        name="fechaPublicacion"
                                    />
                                </FormGroup>
                                <FormGroup>
                                    <label>Nombre del Autor:</label>
                                    <input
                                        style={{marginLeft: '10px'}}
                                        className={"input"}
                                        type="text"
                                        name="autorNombre"
                                    />
                                </FormGroup>
                                <button id={"createSubmitBtn"} type={"submit"} style={{display: 'none'}}>
                                    Submit
                                </button>
                            </form>

                        </ModalBody>

                        <ModalFooter>
                            <Button color="primary" onClick={onCreateButton}>Insertar</Button>
                            <Button color="danger" onClick={handleModalInsertHide}>Cancelar</Button>
                        </ModalFooter>
                    </Modal>

                    <Modal isOpen={modalEdit}>
                        <ModalHeader>
                            <div><h3>Editar Libro</h3></div>
                        </ModalHeader>

                        <ModalBody>
                            {editingLibro !== undefined ?
                                <>
                                    <form onSubmit={onEditSubmit}>
                                        <FormGroup>
                                            <label>Título:</label>
                                            <input
                                                className={"input"}
                                                type="text"
                                                name="tituloEdit"
                                                value={editingLibro.titulo}
                                                onChange={handleTituloChange}
                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <label>Fecha de Publicación:</label>
                                            <input
                                                className={"input"}
                                                type="date"
                                                name="fechaPublicacionEdit"
                                                value={editingLibro.fechaPublicacion}
                                                onChange={handleFechaPublicacionChange}
                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <label>Nombre del Autor:</label>
                                            <input
                                                className={"input"}
                                                type="text"
                                                name="autorNombreEdit"
                                                value={editingLibro.autorNombre}
                                                onChange={handleAutorIdChange}
                                            />
                                        </FormGroup>
                                        <button id={"editSubmitBtn"} type={"submit"} style={{display: 'none'}}>
                                            Submit
                                        </button>
                                    </form>
                                </> :
                                <></>

                            }
                        </ModalBody>

                        <ModalFooter>
                            <Button color="primary" onClick={onEditButton}>Editar</Button>
                            <Button color="danger" onClick={handleModalEditHide}>Cancelar</Button>
                        </ModalFooter>
                    </Modal>
                </VStack>
            </Center>
        </NavBar>
    )
        ;
}

export default LibroPage;