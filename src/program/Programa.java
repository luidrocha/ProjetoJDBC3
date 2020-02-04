package program;

// Inclui registro na base JDBC
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;
import db.DbException;

public class Programa {

	public static void main(String[] args) throws SQLException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Connection conn = null;
		PreparedStatement st = null;

		try {

			conn = DB.getConnection();
			st = conn.prepareStatement("INSERT INTO seller " + "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ "VALUES" + "( ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); // Retorna o u,timo obj criado
																					// Statement.NO_GENERATED_KEYS

			st.setString(1, "Jose Luis");
			st.setString(2, "luidrocha@gmail.com");

			st.setDate(3, new java.sql.Date(sdf.parse("07/06/1974").getTime()));
			st.setDouble(4, 3500.0);
			st.setInt(5, 2);

			int linhasAfetadas = st.executeUpdate(); // Atualiza a base com a Sql. Retorna o numero de linhas
														// atualizadas

			if (linhasAfetadas > 0) {

				ResultSet rs = st.getGeneratedKeys(); // Retorna o objeto inserido podendo ser mais de um

				while (rs.next()) {

					int id = rs.getInt(1); // Numero coluna retornar
					System.out.println("ID " + id);
				}

			} else {
				System.out.println(" Nenhuma linha afetada !");
			}

			System.out.println("Feito ! Linhas Afetadas" + linhasAfetadas);

		} catch (SQLException e) {

			throw new DbException(e.getMessage());

		} catch (ParseException e) {

			e.printStackTrace();
		}

		finally {

			DB.closeStatemant(st);
			DB.closeConnection();
		}

	}
}