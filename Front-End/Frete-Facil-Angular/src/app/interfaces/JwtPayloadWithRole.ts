import { JwtPayload } from 'jwt-decode';

export interface JwtPayloadWithRole extends JwtPayload {
  role: string;
}
