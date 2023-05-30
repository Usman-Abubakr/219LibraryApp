package com.sparta.mg.libraryapplication.controller;

import com.sparta.mg.libraryapplication.exceptions.AuthorDTONotFoundException;
import com.sparta.mg.libraryapplication.model.dtos.AuthorDTO;
import com.sparta.mg.libraryapplication.model.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/authors")
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll();
    }

    @PostMapping("/author")
    public AuthorDTO addAuthor(@RequestBody AuthorDTO authorDTO) {
        return authorRepository.save(authorDTO);
    }

    @GetMapping("/author/{id}")
    public AuthorDTO getAuthorByIdPath(@PathVariable Integer id) {
        return authorRepository.findById(id).get();
    }

    @GetMapping("/author")
    public AuthorDTO getAuthorByNameParam(@RequestParam(name = "name")String name) throws AuthorDTONotFoundException {
        return authorRepository.findByNameSQL(name).orElseThrow(() -> new AuthorDTONotFoundException(name));
    }

    @PutMapping("/author/{id}")
    public AuthorDTO replaceAuthorById(@RequestBody AuthorDTO authorDTO, @PathVariable Integer id) {
        if (authorRepository.findById(id).isPresent()) {
            AuthorDTO oldAuthorDTO = authorRepository.findById(id).get();
            oldAuthorDTO.setFullName(authorDTO.getFullName());
            return authorRepository.save(oldAuthorDTO);
        } else {
            return authorRepository.save(authorDTO);
        }
    }
}
