import React from "react";
import { AbsoluteCenter, Box, Button, Image, Heading, Text, VStack, HStack } from "@chakra-ui/react";
import { Link } from 'react-router-dom';
import { useAuthContext } from "../context/AuthContext.jsx";
import NavBar from "../components/NavBar.jsx";

function HomePage() {
    const { authData, login, logout } = useAuthContext();
    const handleLogOut = () => {
        logout();
    }

    return (
        <Box w="100vw" h="100vh" bg="url('b1.jpg')" bgSize="cover" bgRepeat="no-repeat" bgPosition="center">
            <NavBar>
                <AbsoluteCenter mt="1px">
                    <VStack bg="rgba(0, 0, 0, 0.5)" borderRadius="md" p={5}>
                        <Heading as={"h2"} color="white" textShadow="2px 2px 2px rgba(0, 0, 0, 0.3)">
                            Bienvenido a Mi Aplicación
                        </Heading>
                        <Text color="white" textShadow="1px 1px 1px rgba(0, 0, 0, 0.3)">
                            Esta es una aplicación de ejemplo construida con React y Spring.
                            Utiliza los enlaces de navegación para explorar la aplicación.
                            Aquí puedes encontrar información sobre autores y libros.
                        </Text>
                        <HStack spacing={4}>
                            <Link to={"/libro"}>
                                <Button>
                                    <HStack spacing={2}>
                                        <Image src="book.png" boxSize="30px" />
                                        <Text color="white" textShadow="1px 1px 1px rgba(0, 0, 0, 0.3)">Books</Text>
                                    </HStack>
                                </Button>
                            </Link>
                            <Link to={"/author"}>
                                <Button>
                                    <HStack spacing={2}>
                                        <Image src="aldean.png" boxSize="30px" />
                                        <Text color="white" textShadow="1px 1px 1px rgba(0, 0, 0, 0.3)">Authors</Text>
                                    </HStack>
                                </Button>
                            </Link>
                        </HStack>
                    </VStack>
                </AbsoluteCenter>
            </NavBar>
        </Box>
    )
}

export default HomePage;
