package fr.urssaf.image.commons.dao.spring.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.urssaf.image.commons.dao.spring.modele.Auteur;
import fr.urssaf.image.commons.dao.spring.modele.Document;

@Repository("documentDao")
public class DocumentJdbcDaoImpl implements DocumentDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public DocumentJdbcDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Document find(Integer id) {

		StringBuffer sql = new StringBuffer();
		sql.append("select doc.id,doc.titre,doc.date,doc.id_auteur,aut.nom ");
		sql.append("from document doc ");
		sql.append("left outer join auteur aut on doc.id_auteur=aut.id ");
		sql.append("where doc.id= ? ");

		// String sql =
		// "select id, first_name, last_name from T_ACTOR where id = ?";
		RowMapper mapper = new RowMapper() {
			public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {

				Document document = new Document();
				document.setId(resultSet.getInt("doc.id"));
				document.setTitre(resultSet.getString("doc.titre"));
				document.setDate(resultSet.getDate("doc.date"));

				if (resultSet.getString("doc.id_auteur") != null) {

					Auteur auteur = new Auteur();
					auteur.setId(resultSet.getInt("doc.id_auteur"));
					auteur.setNom(resultSet.getString("aut.nom"));

					document.setAuteur(auteur);

				}

				return document;
			}
		};

		return (Document) jdbcTemplate.queryForObject(sql.toString(),
				new Object[] { id }, mapper);
	}

	@Override
	public Document get(Integer id) {
		return this.find(id);
	}

	@Override
	public void delete(Document obj) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public void save(Document document) {

		StringBuffer sql = new StringBuffer();
		sql.append("insert into document ");
		sql.append("(id_auteur, titre, date) ");
		sql.append("values (?, ?, ?) ");

		this.jdbcTemplate.update(sql.toString(), new Object[] {
				document.getAuteur() != null ? document.getAuteur().getId()
						: null, document.getTitre(), document.getDate() });

		int id = this.jdbcTemplate.queryForInt("select LAST_INSERT_ID()");

		document.setId(id);
	}

	@Override
	public void update(Document obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public int count() {
		return this.jdbcTemplate.queryForInt("select count(*) from document");
	}
}
