export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  adminId: string;
  token: string;
}

export interface AdminRegisterRequest {
  email: string;
  password: string;
  nomAdmin: string;
  prenomAdmin: string;
}