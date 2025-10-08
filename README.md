# Auracle

## Environment Setup

### Backend
1. Copy `backend/src/main/resources/application.properties.example` to `application-local.properties`
2. Fill in your MongoDB connection string
3. Set environment variable: `SPRING_PROFILES_ACTIVE=local`

### Frontend
1. Copy `frontend/.env.example` to `.env.local`
2. Fill in your Clerk and AWS credentials