package com.fastcampus.projectboard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")

})

@Entity
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql 의 Auto increment 방식
    private Long id; // 위의 두가지가 primary 처리 ,  jpa가 자동으로 세팅해주는 것이기 때문에 setter를 붙이지 않음. 그래서 전체 클래스에 setter 를 걸지 않음.


    @Setter
    @Column(nullable = false)
    private String title; // 제목
    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 본문

    @Setter
    private String hashtag; // 해시태그

    @ToString.Exclude // 투스트링을 끊어주는 것.
    @OrderBy("id")
    @OneToMany(mappedBy = "article_id", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>(); // 이 아티클에 해당하는 코멘트는 중복을 허락하지 않고 다 여기에서 보겠다.

//    @CreatedDate
//    @Column(nullable = false)
//    private LocalDateTime createdAt; // 생성일시
//    @CreatedBy
//    @Column(nullable = false, length = 100)
//    private String createdBy; // 생성자
//    @LastModifiedDate
//    @Column(nullable = false)
//    private LocalDateTime modifiedAt; // 수정일시
//    @LastModifiedBy
//    @Column(nullable = false, length = 100)
//    private String modifiedBy; // 수정자



    protected Article() {

    }

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }


    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
