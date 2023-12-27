export interface LoginResponse {
  result: boolean;
  data: {
    token: string;
  };
  message?: string;
}
