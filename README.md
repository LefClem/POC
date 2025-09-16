# 💬 POC for a Chat

A simple **Proof of Concept (POC)** implementing a chat application.  
It consists of a **backend** and a **frontend**, plus a `script.sql` file to set up the database schema/data.

---

## 🚀 Getting Started

Follow these steps to get the project running locally.

### ✅ Prerequisites

Make sure you have installed:

- [Node.js](https://nodejs.org/) (for the frontend)
- [Java / JDK](https://adoptium.net/) (for the backend)
- [Git](https://git-scm.com/) (to clone the repo)

---

### SQL Scripts

The scripts included in this project are not necessary for the launch of the project, it works with a h2 database for the moment.

### 📦 Installation & Setup

1. **Clone the repository**

   ```bash
   git clone https://github.com/LefClem/POC.git
   cd POC

2. **Backend
  
  Configure environment variables (e.g. database URL, ports).
  Build & run:
  ```bash
  cd backend
  mvn spring-boot:run
  ```
3. **Frontend

Install dependencies:
```bash
cd frontend
npm install
```

Then you can launch the frontend:

```bash
npm start
```


Running the Project

Once everything is running:
Backend available at: http://localhost:8080 (default)
Frontend available at: http://localhost:3000
Open the frontend in your browser — the chat app should connect to the backend 🎉
