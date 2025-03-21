package com.example.userBorrowBook.service;

import com.example.userBorrowBook.model.Borrow;
import com.example.userBorrowBook.repository.BorrowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowService {

    private final BorrowRepository borrowRepository;

    public BorrowService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    public List<Borrow> getAllBorrows() {
        return borrowRepository.findAll();
    }

    public Optional<Borrow> getBorrowById(String id) {
        return borrowRepository.findById(id);
    }

    public Borrow createBorrow(Borrow borrow) {
        return borrowRepository.save(borrow);
    }

    public Optional<Borrow> updateBorrow(String id, Borrow borrowDetails) {
        return borrowRepository.findById(id).map(existingBorrow -> {
            existingBorrow.setBorrowDate(borrowDetails.getBorrowDate());
            existingBorrow.setReturnDate(borrowDetails.getReturnDate());
            existingBorrow.setReturned(borrowDetails.isReturned());
            existingBorrow.setPoints(borrowDetails.getPoints());
            existingBorrow.setUser(borrowDetails.getUser());
            existingBorrow.setBook(borrowDetails.getBook());
            return borrowRepository.save(existingBorrow);
        });
    }

    public boolean deleteBorrow(String id) {
        if (borrowRepository.existsById(id)) {
            borrowRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
