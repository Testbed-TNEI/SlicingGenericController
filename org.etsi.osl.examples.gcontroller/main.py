from fastapi import FastAPI, Request
from fastapi.responses import JSONResponse

app = FastAPI()

@app.api_route("/{full_path:path}", methods=["GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"])
async def catch_all(full_path: str, request: Request):
    body = await request.body()
    print(f"Path: /{full_path}")
    print(f"Method: {request.method}")
    print(f"Headers: {dict(request.headers)}")
    print(f"Body: {body.decode('utf-8', errors='replace')}")

    return JSONResponse(status_code=400, content={"detail": "Bad Request"})
