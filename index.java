// backend/src/models/Course.js
const pool = require('../config/database');

class Course {
    static async findAll() {
        const [rows] = await pool.query(`
            SELECT * FROM cursos 
            WHERE deleted_at IS NULL 
            ORDER BY created_at DESC
        `);
        return rows;
    }

    static async findById(id) {
        const [rows] = await pool.query(
            'SELECT * FROM cursos WHERE id = ? AND deleted_at IS NULL',
            [id]
        );
        return rows[0];
    }

    static async create(courseData) {
        const { titulo, descricao, valor, link_acesso, senha_acesso, imagem_url } = courseData;
        const [result] = await pool.query(
            `INSERT INTO cursos 
            (titulo, descricao, valor, link_acesso, senha_acesso, imagem_url) 
            VALUES (?, ?, ?, ?, ?, ?)`,
            [titulo, descricao, valor, link_acesso, senha_acesso, imagem_url]
        );
        return result.insertId;
    }

    static async update(id, courseData) {
        const { titulo, descricao, valor, link_acesso, senha_acesso, imagem_url } = courseData;
        await pool.query(
            `UPDATE cursos SET 
            titulo = ?, descricao = ?, valor = ?, 
            link_acesso = ?, senha_acesso = ?, imagem_url = ?,
            updated_at = CURRENT_TIMESTAMP 
            WHERE id = ?`,
            [titulo, descricao, valor, link_acesso, senha_acesso, imagem_url, id]
        );
    }

    static async delete(id) {
        await pool.query(
            'UPDATE cursos SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?',
            [id]
        );
    }
}

module.exports = Course;