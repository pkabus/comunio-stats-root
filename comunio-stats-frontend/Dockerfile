# pull official base image
FROM node:lts-buster

# set working directory
WORKDIR /usr/src/app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# install app dependencies
COPY package*.json ./
RUN npm install --silent

# add app
COPY . .

# environment variables
ENV REACT_APP_BACKEND_URL http://localhost:8080

# build application
RUN npm run build

# Install `serve` to run the application.
RUN npm install -g serve

# Run
CMD ["serve", "-s", "build"]

# Expose port
EXPOSE 3000

# docker run -it --rm -p 1337:3000 comunio-stats-frontend:prod
#
# sources
# https://mherman.org/blog/dockerizing-a-react-app/
# https://nodejs.org/en/docs/guides/nodejs-docker-webapp/
# https://medium.com/ai2-blog/dockerizing-a-react-application-3563688a2378
