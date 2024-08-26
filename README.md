# BugTicketApplication

BugTicketApplication is a robust bug ticketing application designed to help teams efficiently manage software projects and track issues (tickets) related to those projects. The application is built using Spring Boot for the backend and React for the frontend, offering a seamless user experience and powerful data management capabilities. Additionally, it integrates with JIRA's API for advanced project and ticket management.

## Key Features

- **Spring Boot Backend**: The backend is powered by Spring Boot, providing a secure and scalable environment for managing project and ticket data.
- **React Frontend**: The user interface is built with React, offering a dynamic and responsive experience.
- **JIRA Integration**: The application incorporates an external API from JIRA, allowing users to create and manage projects and tickets directly within JIRA.
- **CRUD Operations**: Perform full CRUD (Create, Read, Update, Delete) operations on both projects and tickets.
- **Authentication & Authorization**: Secure access to the application with role-based authentication and authorization, ensuring that only authorized users can perform specific actions.

## JIRA Integration Setup

To enable JIRA integration, configure the following in your backend application properties:

```properties
jira.api.url=https://your-jira-instance.atlassian.net
jira.api.user=your-jira-username
jira.api.token=your-jira-api-token
