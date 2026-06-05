import express from 'express';
import * as taskServices from '../service/taskService.js';

const router = express.Router();
router.post("/createtask", async(req, res) => {
    res.json(await taskServices.createTask(req.body, req.headers["token"]));
});
export default router;
