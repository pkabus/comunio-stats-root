/* properties.js */

export const backend_properties = {
    backend_host: process.env.REACT_APP_BACKEND_HOST ? process.env.REACT_APP_BACKEND_HOST : "api-comuniohelper.ddns.net",
    backend_port: process.env.REACT_APP_BACKEND_PORT ? process.env.REACT_APP_BACKEND_PORT : "8080"
};