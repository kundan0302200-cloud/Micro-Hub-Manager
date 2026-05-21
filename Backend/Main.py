from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware


app = FastAPI()
origins = ["http://localhost:5173"]

@app.get("/")
def home():
    return "Started FastAPI on 15.4.2026...."

@app.get("/klu")
def klu1():
    return "Welcome to Y26 S4 class on 15.4.2026...."