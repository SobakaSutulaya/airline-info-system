:root{
  --bg1:#5f77f1;
  --bg2:#7a4bb3;

  --card:#ffffff;
  --text:#1f2937;
  --muted:#6b7280;

  --primary:#5b7cff;
  --primary2:#6a62ff;

  --tableHead:#6a4bb6;
  --line:#e9edf6;

  --shadow:0 18px 45px rgba(15,23,42,.28);
  --shadowSmall:0 6px 18px rgba(15,23,42,.12);

  --radius:22px;
}

*{box-sizing:border-box}
body{
  margin:0;
  font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", Arial, sans-serif;
  color:var(--text);
  background: linear-gradient(135deg,var(--bg1),var(--bg2));
  min-height:100vh;
}

/* ===== Header ===== */
header{
  background: rgba(255,255,255,.95);
  backdrop-filter: blur(10px);
  position: sticky;
  top:0;
  z-index:100;
  box-shadow: var(--shadowSmall);
}

.container{
  max-width: 1200px;
  margin:0 auto;
  padding:0 24px;
}

.navbar{
  height:74px;
  display:flex;
  align-items:center;
  justify-content:space-between;
  gap:24px;
}

.logo{
  display:flex;
  align-items:center;
  gap:10px;
  font-weight:800;
  color:#2f49d1;
  font-size:18px;
}

.nav-links{
  list-style:none;
  display:flex;
  align-items:center;
  gap:18px;
  margin:0;
  padding:0;
}

.nav-links a{
  text-decoration:none;
  color:#364152;
  font-weight:600;
  padding:10px 10px;
  border-radius:12px;
  transition:.2s;
}

.nav-links a:hover{
  background:#eef2ff;
  color:#2f49d1;
}

/* ===== Buttons ===== */
.btn{
  display:inline-flex;
  align-items:center;
  justify-content:center;
  border:none;
  cursor:pointer;
  text-decoration:none;
  font-weight:800;
  padding:10px 18px;
  border-radius:999px;
  transition:.2s;
  white-space:nowrap;
}

.btn-primary{
  color:#fff;
  background: linear-gradient(135deg,var(--primary),var(--primary2));
  box-shadow: 0 10px 20px rgba(90,103,216,.25);
}
.btn-primary:hover{ transform: translateY(-1px); }

.btn-outline{
  background:#fff;
  border:1px solid #d7def0;
  color:#364152;
}
.btn-outline:hover{
  border-color:#8aa0ff;
  color:#2f49d1;
  transform: translateY(-1px);
}

/* ===== Page layout ===== */
main{
  padding:38px 0 60px;
}

.card{
  background:var(--card);
  border-radius: var(--radius);
  box-shadow: var(--shadow);
  padding: 30px 30px 24px;
}

.card-title{
  font-size:34px;
  font-weight:900;
  margin:0 0 18px;
}

/* ===== Table ===== */
.table-wrap{
  margin-top: 12px;
  border-radius: 16px;
  overflow:hidden;
  border:1px solid #eef2ff;
}

table{
  width:100%;
  border-collapse: collapse;
  font-size:14px;
}

thead{
  background: var(--tableHead);
  color:#fff;
}

th, td{
  padding:12px 14px;
  border-bottom:1px solid var(--line);
  text-align:left;
}

tbody tr:hover{
  background:#f7f8ff;
}

td:last-child, th:last-child{
  text-align:left;
}

/* ===== Status badges ===== */
.badge{
  display:inline-flex;
  align-items:center;
  padding:6px 12px;
  border-radius:999px;
  font-size:12px;
  font-weight:900;
  line-height:1;
}

.badge-ok{ background:#d1fae5; color:#065f46; }
.badge-warn{ background:#fee2e2; color:#991b1b; }
.badge-off{ background:#e5e7eb; color:#334155; }

/* Small helpers */
.row-between{
  display:flex;
  align-items:center;
  justify-content:space-between;
  gap:14px;
  flex-wrap:wrap;
}
.muted{ color:var(--muted); font-weight:600; }
