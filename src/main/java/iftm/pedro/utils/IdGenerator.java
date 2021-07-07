package iftm.pedro.utils;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

@SuppressWarnings("unchecked")
public class IdGenerator implements IdentifierGenerator, Configurable {

    private String prefix;
    private String identifier;

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
        prefix = properties.getProperty("prefix");
        identifier = properties.getProperty("identifier");
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) throws HibernateException {
        String query = String.format("SELECT %s FROM %s",
                session.getEntityPersister(o.getClass().getName(),o).getIdentifierPropertyName(),o.getClass().getName());


        long max = session.createQuery(query).stream()
                .mapToLong(id -> Long.parseLong(id.toString().replace(prefix + "-" + identifier,"")))
                .max()
                .orElse(0L);

        return prefix + "-" + identifier + (max + 1);
    }
}
