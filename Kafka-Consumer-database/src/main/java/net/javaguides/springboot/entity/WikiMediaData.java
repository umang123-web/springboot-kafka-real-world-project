package net.javaguides.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
@Entity
@Getter

@Table(name = "wikimedia_recentchange")
public class WikiMediaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@Lob
    private String wikiEventData;

    public void setId(Long id) {
        this.id = id;
    }

    public void setWikiEventData(String wikiEventData) {
        this.wikiEventData = wikiEventData;
    }
}
