import express from 'express';
import * as taskServices from '../service/taskService.js';

const router = express.Router();
router.post("/createtask", async(req, res) => {
    res.json(await taskServices.createTask(req.body, req.headers["token"]));
});
export default router;
router.get("/getalltasks/:PAGE/:SIZE", async(req, res) => {
    const { PAGE, SIZE } = req.params;
    const response = await taskServices.getAllTasks(PAGE, SIZE, req.headers["token"]);
    res.json(response);
});
router.delete("/deletetask/:ID", async(req, res) => {
    const { ID } = req.params;
    const response = await taskServices.deleteTask(ID, req.headers["token"]);
    res.json(response);
});
router.put("/updatetask/:ID", async(req, res) => {
    const { ID } = req.params;
    const response = await taskServices.updateTask(ID, req.body, req.headers["token"]);
    res.json(response);
});