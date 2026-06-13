import Tasks from "../models/task.js";
import * as vectorService from "./vectorService.js";
import dotenv from 'dotenv';
import jwt from 'jsonwebtoken';

dotenv.config();

const SECRETE_KEY = process.env.SECRETE_KEY;

export async function createTask(data, token) {
    console.log("DATA:", data);
    console.log("TOKEN:", token);
    try {
        const payload = jwt.verify(token, SECRETE_KEY);
        data.createdby = payload.crid;
        data.vector = await vectorService.generateVector(data.title + " " + data.description);
        await Tasks.create(data); //Insert into MongoDB
        return { code: 200, message: "New task has been created" };
    } catch (error) {
        return { code: 500, message: error.message };
    }
}
export async function getAllTasks(page, size, token) {
    let response;

    try {
        const payload = jwt.verify(token, SECRETE_KEY); // Authorization
        const skip = (page - 1) * size;

        const tasks = await Tasks.find({ createdby: payload.crid })
            .skip(skip) // Skip records for pagination
            .limit(size) // No of records to be fetched per page
            .sort({ _id: 1 }); // Ascending order by _id (-1 for Descending order)

        const totalrecords = await Tasks.countDocuments({
            createdby: payload.crid
        });

        response = {
            code: 200,
            page: page,
            size: size,
            totalpages: Math.ceil(totalrecords / size),
            tasks: tasks
        };
    } catch (e) {
        response = {
            code: 500,
            message: e.message
        };
    }

    return response;
}
export async function deleteTask(id, token) {
    let response;
    try {
        const payload = jwt.verify(token, SECRETE_KEY); //Autorization

        await Tasks.findOneAndDelete({ _id: id });

        response = { code: 200, message: "Task has been deleted" };
    } catch (e) {
        response = { code: 500, message: e.message };
    }
    return response;
}

export async function getTask(id, token) {
    try {
        const payload = jwt.verify(token, SECRETE_KEY);
        const task = await Tasks.findOne({ _id: id, createdby: payload.crid });

        if (!task)
            return { code: 404, message: "Task not found" };

        return { code: 200, task };
    } catch (e) {
        return { code: 500, message: e.message };
    }
}

export async function updateTask(id, data, token) {
    let response;
    try {
        const payload = jwt.verify(token, SECRETE_KEY); //Autorization  
        const { _id, createdby, createdat, updatedat, ...updateData } = data;
        updateData.vector = await vectorService.generateVector(data.title + " " + data.description);

        const task = await Tasks.findOneAndUpdate({ _id: id, createdby: payload.crid },
            updateData, { runValidators: true }
        );

        if (!task)
            return { code: 404, message: "Task not found" };

        response = { code: 200, message: "Task has been updated" };
    } catch (e) {
        response = { code: 500, message: e.message };
    }
    return response;
}

//Vector Search
export async function vectorSearch(query, token) {
    let response;
    try {
        const payload = jwt.verify(token, SECRETE_KEY); //Authoeization

        const queryVector = await vectorService.generateVector(query);

        const tasks = await Tasks.find({ createdby: payload.crid });

        const searchResult = tasks.map(task => {
                const similarity = vectorService.cosineSimilarity(queryVector, task.vector || []);
                console.log(task.title, similarity);
                return {...task._doc, similarity };
            })
            .filter(task => task.similarity > 0.10)
            .sort((a, b) => b.similarity - a.similarity)
            .slice(0, 5);

        response = { code: 200, tasks: searchResult };
    } catch (e) {
        response = { code: 500, message: e.message };
    }
    return response;
}
