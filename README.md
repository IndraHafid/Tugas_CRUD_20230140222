## Dokumentasi - Tugas CRUD

Tampilan Web
<img width="1919" height="971" alt="image" src="https://github.com/user-attachments/assets/9f8edd37-cbbe-4bc3-9a91-4f4977433367" />

Tampilan Database 
<img width="1919" height="1014" alt="image" src="https://github.com/user-attachments/assets/83e33122-c0a9-4ef0-b635-d6786042b43f" />

---

### 1. Create User
**Method:** `POST`  
**Endpoint:** `/ktp`

**Request Body:**
```json
{
  "nomorKtp": "1234567890123499",
  "namaLengkap": "Indra ",
  "alamat": "Kasihan, Bantul",
  "tanggalLahir": "2010-06-09",
  "jenisKelamin": "Laki-laki"
}
```

**Response (201 Created):**
```json
{
    "id": 4,
    "nomorKtp": "1234567890123499",
    "namaLengkap": "Indra ",
    "alamat": "Kasihan, Bantul",
    "tanggalLahir": "2010-06-09",
    "jenisKelamin": "Laki-laki"
}
```
**Request Body (faild):**
```json
{
  "nomorKtp": "123456789012345",
  "namaLengkap": "Indra ",
  "alamat": "Kasihan, Bantul",
  "tanggalLahir": "2010-06-09",
  "jenisKelamin": "Laki-laki"
}
```
Jika Nomor KTP Kurang Dari 16
**Response (400 Bad Request):**
```json
{
    "nomorKtp": "Nomor KTP harus 16 digit angka"
}
```
---
### 2. Get All Users
**Method:** `GET`  
**Endpoint:** `/ktp`

**Response (200 OK):**
```json
[
    {
        "id": 3,
        "nomorKtp": "1234567890123456",
        "namaLengkap": "Indra Hafid Saputra",
        "alamat": "Kasihan, Bantul",
        "tanggalLahir": "2005-06-09",
        "jenisKelamin": "Laki-laki"
    },
    {
        "id": 4,
        "nomorKtp": "1234567890123499",
        "namaLengkap": "Indra ",
        "alamat": "Kasihan, Bantul",
        "tanggalLahir": "2010-06-09",
        "jenisKelamin": "Laki-laki"
    }
]
```
---
### 3. Update User
**Method:** `PUT`  
**Endpoint:** `/ktp/{id}`

**Request Body:**
```json
{
  "nomorKtp": "1234567890123499",
  "namaLengkap": "Indra Update Nih",
  "alamat": "Juwiring, Klaten",
  "tanggalLahir": "2010-06-09",
  "jenisKelamin": "Laki-laki"
}
```

**Response (200 OK):**
```json
{
    "id": 4,
    "nomorKtp": "1234567890123499",
    "namaLengkap": "Indra Update Nih",
    "alamat": "Juwiring, Klaten",
    "tanggalLahir": "2010-06-09",
    "jenisKelamin": "Laki-laki"
}
```
Jika Memasukan ID yang tidak Ada
**Response (400 Bad Request):**
```json
Data KTP dengan id 5 tidak ditemukan
```

---

### 4. Delete User
**Method:** `DELETE`  
**Endpoint:** `/ktp/{id}`

**Response (200 OK):**
```json
Data KTP berhasil dihapus
```
