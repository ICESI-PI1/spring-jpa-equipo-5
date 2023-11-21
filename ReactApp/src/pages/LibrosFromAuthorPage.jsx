import React, { useState, useEffect } from "react";
import { Center, Table, Tbody, Td, Th, Thead, Tr, Text, VStack } from "@chakra-ui/react";
import axios from "../axios.js";
import NavBar from "../components/NavBar.jsx";
import { Button } from "reactstrap";
import instance from "../axios.js";

function LibrosFromAuthorPage() {
    const [authorName, setAuthorName] = useState("")
    const [authorId, setAuthorId] = useState("");
    const [books, setBooks] = useState([]);

    const getIdFromName = async () => {
        try {
            console.log('buscando nombre: ' + authorName)
            await axios.get(instance.getUri() + "/autores" + "/id/" + authorName)
                .then(res => {
                        if (res.status === 200) {
                            console.log('seteando ID !!! lo encontroooo' + authorName)
                            console.log('id seteado' + res.data)
                            setAuthorId(res.data)
                        } else {
                            alert("'Autor' couldn't be edited")
                        }
                    }
                )
        } catch (e) {
            alert("Can't connect with backend")
            console.log(e)
        }
    }

    const fetchBooksByAuthorId = async () => {
        await getIdFromName(authorName)

        try {
            axios.get(`/autores/${authorId}/libros`)
                .then((res) => {
                    console.log("le llegaron los libros")
                    setBooks(res.data);
                });
        } catch (e) {
            console.error("Can't connect with backend", e);
        }
    };

    const backgroundStyle = {
        backgroundImage: "url('b2.jpg')",
        backgroundPosition: 'center',
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
        height: '100vh',
        width: '100vw',
        color: 'white'
    };

    const style_for_input = {
        color: 'black',
    };

    return (
        <NavBar>
            <Center style={backgroundStyle}>
                <VStack spacing={4} align="center">
                    <h1>Libros de un Autor</h1>
                    <Text fontSize="lg">Ingrese el ID del autor para ver sus libros:</Text>
                    <input
                        className={"input"}
                        type="text"
                        value={authorName}
                        style={style_for_input}
                        onChange={(e) => setAuthorName(e.target.value)}
                        placeholder="ID del autor"
                    />
                    <br></br>
                    <Button color="success" onClick={fetchBooksByAuthorId}>
                        Buscar
                    </Button>
                    <br></br>
                    <Table size="md">
                        <Thead>
                            <Tr>
                                <Th>Título</Th>
                                <Th>Fecha de Publicación</Th>
                            </Tr>
                        </Thead>
                        <Tbody>
                            {books.map((book) => (
                                <Tr key={book.titulo}>
                                    <Td>{book.titulo}</Td>
                                    <Td>{book.fechaPublicacion}</Td>
                                </Tr>
                            ))}
                        </Tbody>
                    </Table>
                </VStack>
            </Center>
        </NavBar>
    );
}

export default LibrosFromAuthorPage;
