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
```

## Authentication & Authorization

The application includes built-in authentication and authorization mechanisms:

- **User Roles**: Users are assigned roles (e.g., Admin, Developer) that determine their access level within the application.
- **Protected Routes**: Specific routes and operations are secured, ensuring only authorized users can access or modify data.
- **JWT**: The application uses JWT (JSON Web Token) for secure, stateless authentication.

## Usage

Once both the backend and frontend are running, you can access the application via your web browser at `http://localhost:3000`.

- **Login**: Users must log in to access the application. The login process verifies credentials and assigns roles based on user data.
- **Projects**: Create, update, delete, and view projects. Project access may be restricted based on user roles.
- **Tickets**: Within each project, you can create, update, delete, and view tickets. JIRA integration allows tickets to be synchronized with your JIRA instance.

## Project Structure

- **Backend** (`/backend`): Contains the Spring Boot application with REST API endpoints for managing projects and tickets, as well as authentication and authorization logic.
- **Frontend** (`/frontend`): Contains the React application providing the user interface and handling user authentication.

## Future Improvements

While the BugTicketApplication is functional, there is room for further enhancements:

- **Enhanced JIRA Integration**: Add more functionalities like synchronizing ticket statuses, comments, and attachments with JIRA.
- **UI/UX Improvements**: Enhance the frontend with better design and user experience improvements.
- **Notifications**: Implement real-time notifications for ticket updates, project changes, etc.

