import React from 'react';
import { Routes, Route, BrowserRouter, Navigate } from 'react-router-dom';
import Navbar from './components/NavBar.jsx';
import LibroPage from './pages/libroPage';
import AuthorPage from './pages/authorPage';
import LoginPage from "./pages/LoginPage.jsx";
import HomePage from "./pages/HomePage.jsx";
import ProtectedRoute from "./protectedRoute/ProtectedRoute.jsx";
import { AuthProvider } from "./context/AuthContext";
import LibrosFromAuthorPage from "./pages/LibrosFromAuthorPage.jsx";

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Routes>
                    <Route path='/home' element={<ProtectedRoute><HomePage /></ProtectedRoute>} />
                    <Route path='/libro' element={<ProtectedRoute><LibroPage /></ProtectedRoute>} />
                    <Route path='/author' element={<ProtectedRoute><AuthorPage /></ProtectedRoute>} />
                    <Route path='/librosFromAuthor' element={<ProtectedRoute>
                        <LibrosFromAuthorPage/>
                    </ProtectedRoute>}/>
                    <Route
                        path='/'
                        element={
                            <LoginPage />
                        }
                    />
                    <Route
                        path='*'
                        element={
                            <Navigate to="/" />
                        }
                    />
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;
