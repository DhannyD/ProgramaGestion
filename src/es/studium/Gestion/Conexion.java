package es.studium.Gestion;

import java.awt.Choice;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Conexion {
	String driver = "com.mysql.cj.jdbc.Driver"; // Driver Nativo Tipo 4 de MySQL
	String url = "jdbc:mysql://localhost:3306/ProgramaGestion"; // Ubicaci�n y Nombre de la base de datos
	String user = "root"; // Usuario para conectar
	String password = "Studium2023;"; // Clave del usuario

	Connection connection = null; // Objeto para conectar
	Statement statement = null; // Objeto para lanzar sentencias SQL
	ResultSet resultSet = null;

	Conexion() {
		connection = this.conexion();
	}

	public Connection conexion() {
		try {
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			// Establecer la conexi�n con la BD Empresa
			return (DriverManager.getConnection(url, user, password));
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return null;
	}

	public int comprobarCredenciales(String usuario, String clave) {
		// Montar SQL
		String sentencia = "SELECT * FROM usuarios WHERE nombreUsuario = '" + usuario + "' AND claveUsuario = SHA2('"
				+ clave + "', 256)";
		try {
			// Crear una sentencia tipica o generica
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sentencia SQL
			resultSet = statement.executeQuery(sentencia);
			// Credenciales son correctas
			if (resultSet.next()) {
				int tipoUsuario = resultSet.getInt("tipoUsuario");
				registrarMovimiento(usuario, "Acceso al Sistema");
				return tipoUsuario; // Devuelve el tipo de usuario encontrado
			}
			// Credenciales incorrectas
			else {
				registrarMovimiento(usuario, "Intento de inicio de sesión fallido");
				return -1; // O algún otro valor para representar credenciales incorrectas
			}
		} catch (Exception e) {
			e.printStackTrace(); // Imprime el error para diagnosticar el problema
			return -1; // Manejar cualquier error
		}
	}

	public int NuevoPais(String sentencia) {
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);

			// Obtener el nombre de usuario de la sesión
			String usuario = Sesion.getNombreUsuario();

			registrarMovimiento(usuario, "Alta de nuevo país: " + sentencia);
			return 0;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return 1;
	}

	public String obtenerPaises() {
		String sentencia = "SELECT idPais, isoPais, nombrePais FROM paises";
		String resultado = "";
		try {
			// Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Ejecutar la sentencia
			resultSet = statement.executeQuery(sentencia);
			while (resultSet.next()) {
				resultado = resultado + resultSet.getInt("idPais") + "\t" + resultSet.getString("isoPais") + "\t\t"
						+ resultSet.getString("nombrePais") + "\n";
			}
			String usuario = Sesion.getNombreUsuario();
			registrarMovimiento(usuario, "Obtención de países " + sentencia);
			return resultado;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return "";
	}

	public void rellenarChoicePaises(Choice choPaises) {
		choPaises.removeAll();
		choPaises.add("Elegir el pais...");
		String sentencia = "SELECT idPais, nombrePais FROM paises ORDER BY idPais;";
		try {
			// Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Ejecutar la sentencia
			resultSet = statement.executeQuery(sentencia);
			while (resultSet.next()) {
				choPaises.add(resultSet.getInt("idPais") + "-" + resultSet.getString("nombrePais"));
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public int eliminarPais(String idPais) {
	    // Obtener el nombre de usuario que inició sesión
	    String usuario = Sesion.getNombreUsuario();

	    try {
	        // Crear statement para actualización
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        
	        String eliminarPaises = "DELETE FROM peliculas WHERE idPaisFK = " + idPais + ";";
	        statement.executeUpdate(eliminarPaises);
	        
	        // Eliminar productoras asociadas al país
	        String eliminarProductoras = "DELETE FROM productoras WHERE idPaisFK = " + idPais + ";";
	        statement.executeUpdate(eliminarProductoras);

	        // Eliminar el país
	        String sentencia = "DELETE FROM paises WHERE idPais = " + idPais + ";";
	        statement.executeUpdate(sentencia);

	        // Registrar el movimiento
	        registrarMovimiento(usuario, "Eliminación de país con ID " + idPais);
	        return 0;
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	    return 1;
	}
	
	public String editarPais(String idPais) {
	    // Obtener el nombre de usuario que inició sesión
	    
	    String sentencia = "SELECT * FROM paises WHERE idPais = " + idPais + ";";
	    String resultado = "";
	    try {
	        // Crear una sentencia
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        // Ejecutar la sentencia
	        resultSet = statement.executeQuery(sentencia);
	        if (resultSet.next()) {
	            resultado = resultSet.getString("nombrePais") + "-" + resultSet.getString("isoPais") + "-"
	                    + resultSet.getString("idPais");
	        }
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	    return resultado;
	}

	public int modificarPais(String sentencia) {
		String usuario = Sesion.getNombreUsuario();
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
			registrarMovimiento(usuario, "Edición de país " + sentencia);
			return 0;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return 1;
	}

	public String obtenerProductoras() {
		String usuario = Sesion.getNombreUsuario();
		String sentencia = "SELECT p.idProductora, p.nombreProductora, p.idPaisFK, pa.nombrePais "
				+ "FROM productoras p " + "JOIN paises pa ON p.idPaisFK = pa.idPais";
		String resultado = "";
		try {
			// Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Ejecutar la sentencia
			resultSet = statement.executeQuery(sentencia);
			while (resultSet.next()) {
				resultado = resultado + resultSet.getString("nombreProductora") + "\t" + resultSet.getInt("idPaisFK")
						+ "\t" + resultSet.getString("nombrePais") + "\n";
			}
			registrarMovimiento(usuario, "Obtención de países " + sentencia);
			return resultado;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return "";
	}

	public int NuevaProductora(String sentencia) {
	    // Obtener el nombre de usuario que inició sesión
	    String usuario = Sesion.getNombreUsuario();

	    try {
	        // Crear una sentencia
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        // Ejecutar la sentencia
	        statement.executeUpdate(sentencia);
	        // Registrar el movimiento
	        registrarMovimiento(usuario, "Creación de nueva productora: " + sentencia);
	        return 0; // Éxito
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	    return 1; // Error
	}

	public void rellenarChoiceProductora(Choice choProductoras) {
		choProductoras.removeAll();
		choProductoras.add("Elegir la productora...");
		String sentencia = "SELECT idProductora, nombreProductora FROM productoras ORDER BY idProductora;";
		try {
			// Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Ejecutar la sentencia
			resultSet = statement.executeQuery(sentencia);
			while (resultSet.next()) {
				choProductoras.add(resultSet.getInt("idProductora") + "-" + resultSet.getString("nombreProductora"));
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public int eliminarProductora(String idProductora) {
	    // Obtener el nombre de usuario que inició sesión
	    String usuario = Sesion.getNombreUsuario();  
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        String eliminarPeliculas = "DELETE FROM peliculas WHERE idProductoraFK = " + idProductora + ";";
	        		
	        statement.executeUpdate(eliminarPeliculas);
	        String sentencia = "DELETE FROM productoras WHERE idProductora = " + idProductora + ";";
	        statement.executeUpdate(sentencia);
	        
	        registrarMovimiento(usuario, "Eliminación de productora con ID " + idProductora);
	        return 0;
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	    return 1;
	}
	
	public String editarProductora(String idProductora) {
		String resultado = "";
		String sentencia = "SELECT * FROM productoras WHERE idProductora = " + idProductora + ";";
		 try {
		        // Crear una sentencia
		        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		        // Ejecutar la sentencia
		        resultSet = statement.executeQuery(sentencia);
		        if (resultSet.next()) {
		            resultado = resultSet.getString("idProductora") + "-" + resultSet.getString("nombreProductora") + "-"
		                    + resultSet.getString("idPaisFK");
		        }
		    } catch (Exception e) {
		        System.out.println("Error: " + e.getMessage());
		    }
		return resultado;
	}
	
	public int modificarProductora(String sentencia) {
		String usuario = Sesion.getNombreUsuario();
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
			registrarMovimiento(usuario, "Edición de Productora " + sentencia);
			return 0;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return 1;
	}
	
	public void rellenarChoicePelicula(Choice choPelicula) {
		choPelicula.add("Elegir la pelicula...");
		String sentencia = "SELECT idPelicula, nombrePelicula FROM peliculas ORDER BY idPelicula;";
		try {
			// Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Ejecutar la sentencia
			resultSet = statement.executeQuery(sentencia);
			while (resultSet.next()) {
				choPelicula.add(resultSet.getInt("idPelicula") + "-" + resultSet.getString("nombrePelicula"));
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public int NuevaPelicula(String sentencia) {
	    // Obtener el nombre de usuario que inició sesión
	    String usuario = Sesion.getNombreUsuario();
	    
	    try {
	        // Crear una sentencia
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        // Ejecutar la sentencia
	        statement.executeUpdate(sentencia);
	        // Registrar el movimiento
	        registrarMovimiento(usuario, "Nueva película añadida");
	        return 0; // Éxito
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	    return 1; // Error
	}

	public String obtenerPelicula() {
		String sentencia = "SELECT p.idPelicula, p.nombrePelicula, p.idProductoraFK, pa.nombreProductora "
				+ "FROM peliculas p " + "JOIN productoras pa ON p.idProductoraFK = pa.idProductora";
		String resultado = "";
		try {
			// Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Ejecutar la sentencia
			resultSet = statement.executeQuery(sentencia);
			while (resultSet.next()) {
				resultado = resultado + resultSet.getString("nombrePelicula") + "\t"
						+ resultSet.getString("nombreProductora") + "\n";
			}
			return resultado;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return "";
	}

	public int eliminarPelicula(String idPelicula) {
	    // Obtener el nombre de usuario que inició sesión
	    String usuario = Sesion.getNombreUsuario();
	    
	    String sentencia = "DELETE FROM peliculas WHERE idPelicula = " + idPelicula + ";";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        statement.executeUpdate(sentencia);
	        // Registrar el movimiento
	        registrarMovimiento(usuario, "Película eliminada con ID " + idPelicula);
	        return 0;
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	    return 1;
	}

	public String editarPelicula(String idPelicula) {
		String resultado = "";
		String sentencia = "SELECT * FROM Peliculas WHERE idPelicula = " + idPelicula + ";";
		 try {
		        // Crear una sentencia
		        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		        // Ejecutar la sentencia
		        resultSet = statement.executeQuery(sentencia);
		        if (resultSet.next()) {
		            resultado = resultSet.getString("idPelicula") + "-" + resultSet.getString("nombrePelicula") + "-"
		                    + resultSet.getString("idProductoraFK");
		        }
		    } catch (Exception e) {
		        System.out.println("Error: " + e.getMessage());
		    }
		return resultado;
	}
	
	public int ModificarPelicula(String sentencia) {
		String usuario = Sesion.getNombreUsuario();
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
			registrarMovimiento(usuario, "Edición de Pelicula " + sentencia);
			return 0;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return 1;
	}
	
	public class Sesion {
		private static String nombreUsuario;

		public static String getNombreUsuario() {
			return nombreUsuario;
		}

		public static void setNombreUsuario(String Usuario) {
			Sesion.nombreUsuario = Usuario;
		}
	}

	public static void registrarMovimiento(String usuario, String accion) {
		String archivoLog = "movimientos.log";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String fechaHora = sdf.format(new Date());
		String movimiento = "[" + fechaHora + "][" + usuario + "][" + accion + "]";
		try (PrintWriter out = new PrintWriter(new FileWriter(archivoLog, true))) {
			out.println(movimiento);
			System.out.println("Movimiento registrado con éxito en el archivo de registro.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
