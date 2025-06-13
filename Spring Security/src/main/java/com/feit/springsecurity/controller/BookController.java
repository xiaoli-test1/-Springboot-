package com.feit.springsecurity.controller;

import com.feit.springsecurity.entity.Book;
import com.feit.springsecurity.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/book")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    // ====================== 图书列表 ======================
    @GetMapping("/list")
    public String bookList(Model model) {
        List<Book> books = bookService.findAllBooks();
        System.out.println("获取到的图书数量: " + books.size());
        for (Book book : books) {
            System.out.println("Book: " + book.getTitle() + ", Cover URL: " + book.getCoverUrl());
        }
        model.addAttribute("books", books);
        return "book_list"; // 返回book_list模板
    }

    // ====================== 管理页面（管理员专用） ======================
    @PreAuthorize("hasRole('ADMIN')") // 权限控制：仅管理员可访问
    @GetMapping("/admin/manage")
    public String manageBooks(Model model) {
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        return "book_manage"; // 对应管理页面（假设存在 book_manage.html）
    }

    @GetMapping("/admin/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "book_form";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Book> bookOptional = bookService.findBookById(id);
        if (bookOptional.isPresent()) {
            model.addAttribute("book", bookOptional.get());
            return "book_form";
        } else {
            logger.warn("图书不存在，ID：{}", id);
            model.addAttribute("errorMessage", "图书不存在");
            return "error";
        }
    }

    @PostMapping("/admin/add")
    public String addBook(@ModelAttribute("book") Book book, @RequestParam("cover") MultipartFile cover) {
        // 处理文件上传
        if (!cover.isEmpty()) {
            try {
                // 获取 classpath:/static/images 目录的路径
                ClassPathResource resource = new ClassPathResource("static/images");
                File uploadDir = resource.getFile();
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // 生成唯一的文件名
                String fileName = UUID.randomUUID().toString() + "_" + cover.getOriginalFilename();
                Path filePath = Paths.get(uploadDir.getAbsolutePath(), fileName);

                // 保存文件
                cover.transferTo(filePath.toFile());

                // 将文件相对路径保存到图书对象中，方便前端访问
                book.setCoverUrl("/static/images/" + fileName);
            } catch (IOException e) {
                logger.error("文件上传失败", e);
                return "redirect:/book/admin/manage?error";
            }
        }

        // 处理保存逻辑，例如保存到数据库
        try {
            bookService.saveBook(book);
            logger.info("添加图书成功，ID：{}", book.getId());
            return "redirect:/book/admin/manage";  // 成功后重定向
        } catch (Exception e) {
            logger.error("添加图书失败", e);
            return "redirect:/book/admin/manage?error";  // 失败后重定向
        }
    }

    @PostMapping("/admin/edit")
    public String editBook(@ModelAttribute("book") Book book, @RequestParam("cover") MultipartFile cover) {
        // 处理文件上传
        if (!cover.isEmpty()) {
            try {
                // 获取 classpath:/static/images 目录的路径
                ClassPathResource resource = new ClassPathResource("static/images");
                File uploadDir = resource.getFile();
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // 生成唯一的文件名
                String fileName = UUID.randomUUID().toString() + "_" + cover.getOriginalFilename();
                Path filePath = Paths.get(uploadDir.getAbsolutePath(), fileName);

                // 保存文件
                cover.transferTo(filePath.toFile());

                // 将文件相对路径保存到图书对象中，方便前端访问
                book.setCoverUrl("/static/images/" + fileName);
            } catch (IOException e) {
                logger.error("文件上传失败", e);
                return "redirect:/book/admin/manage?error";
            }
        }

        // 处理编辑逻辑，例如更新数据库中的数据
        try {
            bookService.updateBook(book);
            logger.info("编辑图书成功，ID：{}", book.getId());
            return "redirect:/book/admin/manage";  // 成功后重定向
        } catch (Exception e) {
            logger.error("编辑图书失败", e);
            return "redirect:/book/admin/manage?error";  // 失败后重定向
        }
    }
    // ====================== 删除图书 ======================
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            logger.info("删除图书成功，ID：{}", id);
            return "redirect:/book/admin/manage";
        } catch (Exception e) {
            logger.error("删除图书失败，ID：{}", id, e);
            return "redirect:/book/admin/manage?error";
        }
    }

    // ====================== 阅读图书详情 ======================
    @GetMapping("/read/{id}")
    public String readBook(@PathVariable Long id, Model model) {
        try {
            Optional<Book> bookOptional = bookService.findBookById(id);
            if (bookOptional.isPresent()) {
                Book book = bookOptional.get(); // 获取 Book 对象
                model.addAttribute("book", book); // 传递图书对象到详情页

                // 获取并格式化出版日期
                Date publishDateDate = book.getPublishDate();
                String formattedPublishDate = null;

                if (publishDateDate != null) {
                    // 使用 SimpleDateFormat 来格式化日期
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    formattedPublishDate = sdf.format(publishDateDate);
                }

                model.addAttribute("publishDate", formattedPublishDate); // 将格式化后的字符串添加到模型中
                return "book_read"; // 返回 book_read.html
            } else {
                logger.warn("图书不存在，ID：{}", id);
                model.addAttribute("errorMessage", "图书不存在");
                return "error";
            }
        } catch (Exception e) {
            logger.error("阅读图书失败，ID：{}", id, e);
            model.addAttribute("errorMessage", "读取图书时出现错误，请稍后再试");
            return "error";
        }
    }
}