package com.fastcampus.projectboard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")

})
@Entity
public class ArticleComment extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql 의 Auto increment 방식
    private Long id;

    @Setter @ManyToOne(optional = false) private Article article_id; // 게시글(ID) 객체지향적 표현
    @Setter @Column(nullable = false, length = 500) private String content; // 본문

//    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성일시
//    @CreatedBy @Column(nullable = false,  length = 100) private String createdBy; // 생성자
//    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일시
//    @LastModifiedBy @Column(nullable = false,  length = 100) private String modifiedBy; // 수정자

    protected ArticleComment() {
    }

    private ArticleComment(Article article, String content) {
        this.article_id = article;
        this.content = content;
    }

    public static ArticleComment of(Article article, String content) {
      return new ArticleComment(article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
