import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import '../App.css';

const HomePage = () => {
    const [tasks, setTasks] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:8000/api/tasks")
            .then(response => {
                setTasks(response.data);
            })
            .catch(error => {
                console.error("There was an error fetching the tasks!", error);
            });
    }, []);

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        const options = { weekday: 'long', month: 'long', day: 'numeric' };
        const formattedDate = date.toLocaleDateString('en-US', options);
        return formattedDate;
    }

    const toggleComplete = (taskId) => {
        console.log("Toggle complete called with task ID:", taskId);
        axios.patch(`http://localhost:8000/api/task/${taskId}/toggleComplete`)
            .then(response => {
                console.log("Toggle complete response:", response.data);
                const updatedTask = response.data;
                setTasks(prevTasks => prevTasks.map(task =>
                    task._id === updatedTask._id ? updatedTask : task
                ));
            })
            .catch(error => {
                console.error("There was an error updating the task:", error);
            });
    }

    // Sorting tasks by due date
    const sortedTasks = tasks.slice().sort((a, b) => new Date(a.date) - new Date(b.date));

    return (
        <div class="">
            <table class="table-auto w-full border-collapse bg-gray-700 text-gray-100 mt-5 mb-5">
                <thead>
                    <tr>
                        <th class="px-4 py-2">Tasks</th>
                        <th class="px-4 py-2">Due Date</th>
                        <th class="px-4 py-2">Options</th>
                        <th class="px-4 py-2">Complete</th>
                    </tr>
                </thead>
                <tbody>
                    {sortedTasks.map((task) => (
                        <tr key={task._id} class="bg-gray-600">
                            <td class="px-4 py-2" style={{ textDecoration: task.isCompleted ? 'line-through' : 'none' }}>{task.name}</td>
                            <td class="px-4 py-2" style={{ textDecoration: task.isCompleted ? 'line-through' : 'none' }}>{formatDate(task.date)}</td>
                            <td class="px-4 py-2">
                                <Link class="text-blue-500 font-bold hover:text-blue-600" to={`/dets/${task._id}`}>Details</Link>
                                <span> | </span>
                                <Link class="text-blue-500 font-bold hover:text-blue-600" to={`/update/${task._id}`}>Edit</Link>
                            </td>
                            <td>
                                <button class = "inline-flex items-center px-4 py-2 bg-blue-500 hover:bg-blue-600 text-white font-semibold rounded-lg shadow-md " onClick={() => toggleComplete(task._id)}>
                                    {task.isCompleted ? 'Undo' : 'Complete'}
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
    
}

export default HomePage;