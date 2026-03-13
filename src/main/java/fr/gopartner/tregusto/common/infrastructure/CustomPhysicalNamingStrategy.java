package fr.gopartner.tregusto.common.infrastructure;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomPhysicalNamingStrategy implements PhysicalNamingStrategy {

    @Override
    public Identifier toPhysicalCatalogName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalColumnName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalSchemaName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalSequenceName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalTableName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(identifier);
    }

    private Identifier convertToSnakeCase(final Identifier identifier) {
        // null-safe: return null so Hibernate can handle absent names
        if (identifier == null) {
            return null;
        }

        final String text = identifier.getText();
        // if text is null/empty return original identifier
        if (text == null || text.isBlank()) {
            return identifier;
        }

        // convert camelCase -> snake_case
        final String regex = "([a-z])([A-Z])";
        final String replacement = "$1_$2";
        final String newName = text.replaceAll(regex, replacement).toLowerCase();

        // preserve quoted state
        return Identifier.toIdentifier(newName, identifier.isQuoted());
    }
}
