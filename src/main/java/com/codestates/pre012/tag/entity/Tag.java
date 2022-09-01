package com.codestates.pre012.tag.entity;

import com.codestates.pre012.tag.entity.Tag_Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Builder
@Setter
@Getter
@Entity
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagId;

    @Enumerated(value = EnumType.STRING)
    private TagList tagList;

    @OneToMany(mappedBy = "tag")
    private List<Tag_Posts> tag_postsList;

    //addTag_Posts() 삭제

    public enum TagList {
        JAVA("Java"),
        SPRING("Spring"),
        JAVASCRIPT("Javascript"),
        NODE_JS("Node.js"),
        PYTHON("Python"),
        C1("C"),
        C2("C++");

        private String name;

        public String getName() {
            return name;
        }

        TagList(String name) {
            this.name = name;
        }
    }

}
