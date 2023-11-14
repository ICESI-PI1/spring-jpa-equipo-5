import React from 'react';
import { Box, ChakraProvider, Image, Flex } from '@chakra-ui/react';
import { useAuthContext } from '../context/AuthContext.jsx';
import { Link } from 'react-router-dom';


function Navbar({ children }) {
    const { logout } = useAuthContext()

    const handleLogOut = () => {
        logout();
    }

    return (
        <>
            <ChakraProvider>
                <Box as="nav" display="flex" flexDirection="column" justifyContent="center" width="15%" minWidth="150px" p={4} bg="gray.700" color="white" position="fixed" top="0" left="0" bottom="0">
                    <Box>
                        <Link to="/home">
                            <Box display="flex" alignItems="center" p={2} marginBottom="15px" marginTop="120px">
                                <Image src="/home.png" boxSize="50px" marginRight="4px" />
                                Home
                            </Box>
                        </Link>
                        <Link to="/libro">
                            <Box display="flex" alignItems="center" p={2} marginBottom="15px">
                                <Image src="/book.png" boxSize="35px" marginRight="17px" />
                                Books
                            </Box>
                        </Link>
                        <Link to="/author">
                            <Box display="flex" alignItems="center" p={2} marginBottom="15px" marginTop="10px">
                                <Image src="/aldean.png" boxSize="35px" marginRight="17px" />
                                Authors
                            </Box>
                        </Link>
                        <Link to="/librosFromAuthor">
                            <Box display="flex" alignItems="center" p={2} marginBottom="70px">
                                <Image src="/book.png" boxSize="35px" marginRight="17px" /> {/* You might need to replace 'book-author.png' with an appropriate icon */}
                                Books from Author
                            </Box>
                        </Link>
                        <Link to="/" onClick={handleLogOut}>
                            <Box display="flex" alignItems="center" p={2} marginBottom="10px">
                                <Image src="/ender.png" boxSize="70px" marginRight="5px" />
                                Log out
                            </Box>
                        </Link>
                    </Box>
                </Box>
            </ChakraProvider>
            <div style={{ marginLeft: '15%' }}>
                {children}
            </div>
        </>
    );
}

export default Navbar;
