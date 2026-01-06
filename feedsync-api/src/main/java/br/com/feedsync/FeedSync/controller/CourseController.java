package br.com.feedsync.FeedSync.controller;

import br.com.feedsync.FeedSync.dto.CourseRequest;
import br.com.feedsync.FeedSync.dto.CourseResponse;
import br.com.feedsync.FeedSync.entity.Course;
import br.com.feedsync.FeedSync.mapper.CourseMapper;
import br.com.feedsync.FeedSync.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService service;
    private final CourseMapper mapper;

    public CourseController(CourseService service, CourseMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<CourseResponse> create(@Validated @RequestBody CourseRequest request) {
        Course saved = service.create(mapper.toEntity(request));
        return ResponseEntity.ok(mapper.toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> findById(@PathVariable String id) {
        var course = service.findById(id);
        return ResponseEntity.ok(mapper.toResponse(course));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> findAll() {
        var courses = service.findAll();
        return ResponseEntity.ok(mapper.toResponseList(courses));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> update(@PathVariable String id, @Validated @RequestBody CourseRequest request) {
        Course updated = service.update(id, mapper.toEntity(request));
        return ResponseEntity.ok(mapper.toResponse(updated));
    }
}