import React from "react";

import {
    AbsoluteCenter,
    Button, Center,
    FormControl,
    FormErrorMessage,
    FormLabel, Heading,
    Input, VStack,
} from "@chakra-ui/react";
import axios from "axios";
import instance from "../axios.js";
import {useFormik} from "formik";
import * as Yup from 'yup';
import {useAuthContext} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";

function LoginPage() {
    const {authData, login, logout} = useAuthContext()
    const navigate = useNavigate(); // Para redireccionar al usuario

    const formik = useFormik({
        initialValues: {
            username: "",
            password: "",
        },
        onSubmit: async (values) => {

            try {
                await axios.post(instance.getUri() + "/authenticate", {
                    "username": values.username,
                    "password": values.password
                })
                    .then(res => {
                            localStorage.setItem('token', res.data.token)
                            login()
                            navigate("/home"); // Navegar a home luego de loguearse
                        }
                    )
            } catch (e) {
                if (e.toString().includes("Network Error")) {
                    alert("Can't connect with backend")
                } else {
                    alert("Incorrect password")
                }
                console.log(e)
            }
        },
        validationSchema: Yup.object({
            username: Yup.string().required("Required"),
            password: Yup.string().required("Required")
        }),
    });
    const backgroundStyle = {
        backgroundImage: "url('cielo.jpg')", // Replace 'path_to_your_image.jpg' with the path to your image
        backgroundPosition: 'center',
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
        height: '100vh',
        width: '100vw'
    };
    return (
        <AbsoluteCenter>
            <AbsoluteCenter style={backgroundStyle}>
                <Center>
                    <Heading marginTop={'120px'} marginBottom={'35px'} as="h1">
                        Log-In
                    </Heading>
                </Center>
                <form onSubmit={formik.handleSubmit}>
                <VStack spacing={30}>
                    <FormControl isInvalid={formik.touched.username && formik.errors.username}>
                        <FormLabel htmlFor={"username"} fontSize={'20px'} mb={'5px'}>
                            Username
                        </FormLabel>
                        <Input
                            sx={{'::placeholder': {fontSize: '15px'}}}
                            border={"1px solid #ccc"}
                            borderRadius={"5px"}
                            boxShadow={"0 2px 4px rgba(0, 0, 0, 0.1)"}
                            transition={"border-color 0.3s ease"}
                            id="username" name="username" {...formik.getFieldProps("username")}
                            fontSize={'20px'}
                            placeholder='username'/>
                        <FormErrorMessage className="error-message">
                            {formik.errors.username}
                        </FormErrorMessage>
                    </FormControl>

                    <FormControl isInvalid={formik.touched.password && formik.errors.password}>
                        <FormLabel htmlFor={"password"} fontSize={'20px'} mb={'5px'}>
                            Password
                        </FormLabel>
                        <Input sx={{'::placeholder': {fontSize: '15px'}}}
                               border={"1px solid #ccc"}
                               borderRadius={"5px"}
                               boxShadow={"0 2px 4px rgba(0, 0, 0, 0.1)"}
                               transition={"border-color 0.3s ease"}
                               type={"password"} id="password" name="password" {...formik.getFieldProps("password")}
                               fontSize={'20px'}
                               placeholder='password'/>
                        <FormErrorMessage className="error-message">
                            {formik.errors.password}
                        </FormErrorMessage>
                    </FormControl>

                    <Center>
                        <Button className={"button"} fontSize={'15px'} variant='solid' colorScheme='blue'
                                type={"submit"}
                                onClick={formik.handleSubmit}>
                            Submit
                        </Button>
                    </Center>
                </VStack>
            </form>
        </AbsoluteCenter>
        </AbsoluteCenter>
    )
}

export default LoginPage