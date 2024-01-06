# Base image
FROM node:20

# Create a directory
WORKDIR /app

# Copy files into image
COPY main.js .
COPY package.json .
COPY package-lock.json .
COPY public public
COPY views views

# Install dependencies
RUN npm ci

# Run app
ENV PORT=3000
ENV INSTANCE_NAME="dov-bear"

EXPOSE ${PORT}

ENTRYPOINT node main.js