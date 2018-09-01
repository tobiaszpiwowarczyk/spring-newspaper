package pl.toby.core.misc;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;


@MappedSuperclass
public abstract class BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private final UUID id;

    public BaseEntity() {
        this.id = null;
    }

    public UUID getId() {
        return id;
    }
}
