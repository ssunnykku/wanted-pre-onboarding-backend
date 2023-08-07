class ApiError extends Error {
  status: number;
  message: string;

  constructor(status: number, message: string) {
    super(message);
    this.status = status;
    this.message = message;
  }

  static setBadRequest(message: string) {
    return new ApiError(400, message);
  }

  static setUnauthorized(message: string) {
    return new ApiError(401, message);
  }

  static setForbidden(message: string) {
    return new ApiError(403, message);
  }

  static setNotFound(message: string) {
    return new ApiError(404, message);
  }

  static setInternalServerError(message: string) {
    return new ApiError(500, message);
  }
}

export default ApiError;
