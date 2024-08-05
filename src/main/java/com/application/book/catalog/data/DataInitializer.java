package com.application.book.catalog.data;

import com.application.book.catalog.domain.BookItem;
import com.application.book.catalog.domain.BookItemRepository;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final BookItemRepository bookItemRepository;

    public DataInitializer(BookItemRepository bookItemRepository) {
        this.bookItemRepository = bookItemRepository;
    }

    @Override
    public void run(String... args) {
        if (bookItemRepository.count() > 0) {
            log.info("Data already exists. Skipping data population");
            return;
        }

        bookItemRepository.save(
                new BookItem(
                        null,
                        "P100",
                        "The Hunger Games",
                        "Winning will make you famous. Losing means certain death...",
                        "https://images.gr-assets.com/books/1447303603l/2767052.jpg",
                        new BigDecimal("34.0"),
                        BigDecimal.ZERO,
                        false));
        bookItemRepository.save(
                new BookItem(
                        null,
                        "P101",
                        "To Kill a Mockingbird",
                        "The unforgettable novel of a childhood in a sleepy Southern town and the crisis of conscience that rocked it...",
                        "https://images.gr-assets.com/books/1361975680l/2657.jpg",
                        new BigDecimal("45.40"),
                        BigDecimal.ZERO,
                        false));
        bookItemRepository.save(
                new BookItem(
                        null,
                        "P102",
                        "The Chronicles of Narnia",
                        "Journeys to the end of the world, fantastic creatures, and epic battles between good and evil—what more could any reader ask for in one book?...",
                        "https://images.gr-assets.com/books/1449868701l/11127.jpg",
                        new BigDecimal("44.50"),
                        BigDecimal.ZERO,
                        false));
        bookItemRepository.save(
                new BookItem(
                        null,
                        "P103",
                        "Gone with the Wind",
                        "Gone with the Wind is a novel written by Margaret Mitchell, first published in 1936.",
                        "https://images.gr-assets.com/books/1328025229l/18405.jpg",
                        new BigDecimal("44.50"),
                        BigDecimal.ZERO,
                        false));
        bookItemRepository.save(
                new BookItem(
                        null,
                        "P104",
                        "The Fault in Our Stars",
                        "Despite the tumor-shrinking medical miracle that has bought her a few years, Hazel has never been anything but terminal, her final chapter inscribed upon diagnosis.",
                        "https://images.gr-assets.com/books/1360206420l/11870085.jpg",
                        new BigDecimal("14.50"),
                        BigDecimal.ZERO,
                        false));
        bookItemRepository.save(
                new BookItem(
                        null,
                        "P105",
                        "The Giving Tree",
                        "Once there was a tree...and she loved a little boy.",
                        "https://images.gr-assets.com/books/1174210942l/370493.jpg",
                        new BigDecimal("32.0"),
                        BigDecimal.ZERO,
                        false));
        bookItemRepository.save(
                new BookItem(
                        null,
                        "P106",
                        "The Da Vinci Code",
                        "An ingenious code hidden in the works of Leonardo da Vinci.A desperate race through the cathedrals and castles of Europe",
                        "https://images.gr-assets.com/books/1303252999l/968.jpg",
                        new BigDecimal("14.50"),
                        BigDecimal.ZERO,
                        false));
        bookItemRepository.save(
                new BookItem(
                        null,
                        "P107",
                        "The Alchemist",
                        "Paulo Coelho's masterpiece tells the mystical story of Santiago, an Andalusian shepherd boy who yearns to travel in search of a worldly treasure",
                        "https://images.gr-assets.com/books/1483412266l/865.jpg",
                        new BigDecimal("12.0"),
                        BigDecimal.ZERO,
                        false));
        bookItemRepository.save(
                new BookItem(
                        null,
                        "P108",
                        "Charlotte's Web",
                        "This beloved book by E. B. White, author of Stuart Little and The Trumpet of the Swan, is a classic of children's literature",
                        "https://images.gr-assets.com/books/1439632243l/24178.jpg",
                        new BigDecimal("14.0"),
                        BigDecimal.ZERO,
                        false));
        bookItemRepository.save(
                new BookItem(
                        null,
                        "P109",
                        "The Little Prince",
                        "Moral allegory and spiritual autobiography, The Little Prince is the most translated book in the French language.",
                        "https://images.gr-assets.com/books/1367545443l/157993.jpg",
                        new BigDecimal("16.50"),
                        BigDecimal.ZERO,
                        false));
        bookItemRepository.save(
                new BookItem(
                        null,
                        "P110",
                        "A Thousand Splendid Suns",
                        "A Thousand Splendid Suns is a breathtaking story set against the volatile events of Afghanistan's last thirty years—from the Soviet invasion to the reign of the Taliban to post-Taliban rebuilding—that puts the violence, fear, hope, and faith of this country in intimate, human terms.",
                        "https://images.gr-assets.com/books/1345958969l/128029.jpg",
                        new BigDecimal("15.50"),
                        BigDecimal.ZERO,
                        false));

        log.info("Catalog data initialized");
    }
}
