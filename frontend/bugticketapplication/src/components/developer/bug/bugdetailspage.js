import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import DeveloperBugService from '../../../services/developer/developerbugservice';
import { CircularProgress, Container, Typography, Paper, Button, MenuItem, Select, FormControl, InputLabel, TextField, Snackbar, Alert } from '@mui/material';
import './bugdetailspage.css';
import DeveloperProjectService from '../../../services/developer/developerprojectservice';

function BugDetailsPage() {
    const { bugId, projectId } = useParams();
    const user_id = localStorage.getItem('user_id');
    const navigate = useNavigate();
    const [bug, setBug] = useState(null);
    const [developers, setDevelopers] = useState([]);
    const [selectedDeveloperId, setSelectedDeveloperId] = useState('');
    const [selectedStatus, setSelectedStatus] = useState('');
    const [description, setDescription] = useState('');
    const [loading, setLoading] = useState(true);
    const [alertOpen, setAlertOpen] = useState(false);
    const [alertMessage, setAlertMessage] = useState('');
    const [alertSeverity, setAlertSeverity] = useState('success');
    const developerBugService = new DeveloperBugService();
    const developerProjectService = new DeveloperProjectService();

    useEffect(() => {
        developerBugService.getBugById(bugId)
            .then(data => {
                setBug(data);
                setSelectedDeveloperId(data.developer.id);
                setSelectedStatus(data.status);
                setDescription(data.description);
                setLoading(false);
            })
            .catch(error => {
                console.error('Failed to fetch bug details:', error);
                setLoading(false);
            });

        developerProjectService.getDevelopersOnProject(projectId)
            .then(data => {
                setDevelopers(data);
            })
            .catch(error => {
                console.error('Failed to fetch developers:', error);
            });
    }, [bugId, projectId]);

    const handleDeveloperChange = (event) => {
        setSelectedDeveloperId(event.target.value);
    };

    const handleStatusChange = (event) => {
        setSelectedStatus(event.target.value);
    };

    const handleDescriptionChange = (event) => {
        setDescription(event.target.value);
    };

    const handleSaveChanges = () => {
        const updatedBug = {
            ...bug,
            developer: selectedDeveloperId ,
            status: selectedStatus,
            description: description,
        };

        developerBugService.updateBug(bugId, updatedBug)
            .then(() => {
                setAlertMessage('Bug updated successfully!');
                setAlertSeverity('success');
                setAlertOpen(true);
                setTimeout(() => {
                    navigate(`/project_home_page/${user_id}`);
                }, 2000);
            })
            .catch(error => {
                console.error('Failed to update bug:', error);
                setAlertMessage('Failed to update bug!');
                setAlertSeverity('error');
                setAlertOpen(true);
            });
    };

    const handleAlertClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }
        setAlertOpen(false);
    };

    const handleBackToProject = () => {
        navigate(`/project_home_page/${user_id}`);
    };

    if (loading) {
        return <CircularProgress />;
    }

    return (
        <Container className="bug-details-container">
            <Typography variant="h4" gutterBottom>
                {bug.name}
            </Typography>
            <Paper elevation={3} className="bug-details-paper">
                <FormControl fullWidth>
                    <TextField
                        label="Description"
                        value={description}
                        onChange={handleDescriptionChange}
                        multiline
                        rows={4}
                        variant="outlined"
                        margin="normal"
                    />
                </FormControl>
                <FormControl fullWidth margin="normal">
                    <InputLabel id="status-select-label">Status</InputLabel>
                    <Select
                        labelId="status-select-label"
                        id="status-select"
                        value={selectedStatus}
                        label="Status"
                        onChange={handleStatusChange}
                    >
                        <MenuItem value="OPEN">Open</MenuItem>
                        <MenuItem value="IN_PROGRESS">In Progress</MenuItem>
                        <MenuItem value="CLOSED">Closed</MenuItem>
                    </Select>
                </FormControl>
                {bug.developer && bug.developer.seniority === 'SENIOR' && (
                    <FormControl fullWidth margin="normal">
                        <InputLabel id="developer-select-label">Developer</InputLabel>
                        <Select
                            labelId="developer-select-label"
                            id="developer-select"
                            value={selectedDeveloperId}
                            label="Developer"
                            onChange={handleDeveloperChange}
                        >
                            {developers.map((dev) => (
                                <MenuItem key={dev.id} value={dev.id}>
                                    {dev.appUser.username}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                )}
                <Button variant="contained" color="primary" onClick={handleSaveChanges}>
                    Save Changes
                </Button>
                <Button variant="contained" color="secondary" className="back-button" onClick={handleBackToProject}>
                    Back to Project
                </Button>
            </Paper>
            <Snackbar
                open={alertOpen}
                autoHideDuration={4000}
                onClose={handleAlertClose}
                anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
            >
                <Alert
                    onClose={handleAlertClose}
                    severity={alertSeverity}
                    className={`snackbar-${alertSeverity}`}
                    sx={{ width: '100%' }}
                >
                    {alertMessage}
                </Alert>
            </Snackbar>
        </Container>
    );
}

export default BugDetailsPage;
