package github.konradcam1289.todoapp.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@Embeddable
class Audit {
    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;
    @PrePersist
        //służy do zapisu na bazie
    void prePersist(){
        createdOn = LocalDateTime.now();
    }
    @PreUpdate
    void  preMerge(){
        updatedOn = LocalDateTime.now();
    }
}
