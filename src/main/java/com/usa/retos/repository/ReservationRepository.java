package com.usa.retos.repository;

import com.usa.retos.crud.ReservationCrud;
import com.usa.retos.model.Client;
import com.usa.retos.model.Reservation;
import com.usa.retos.reports.ClientCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepository {
    @Autowired
    private ReservationCrud reservationCrud;

    public List<Reservation> getAll() {
        return (List<Reservation>) reservationCrud.findAll();
    }

    public Optional<Reservation> getReservation(int id) {
        return reservationCrud.findById(id);
    }

    public Reservation save(Reservation reservation) {
        return reservationCrud.save(reservation);
    }

    public void delete(Reservation reservation) {
        reservationCrud.delete(reservation);
    }

    public List<Reservation> ReservationStatus(String status){
        return reservationCrud.findAllByStatus(status);
    }

    public List<Reservation> ReservationTime(Date a, Date b) {
        return reservationCrud.findAllByStartDateAfterAndStartDateBefore(a, b);
    }

    public List<ClientCounter> getTopClients(){
        List<ClientCounter> res = new ArrayList<>();
        List<Object[]> report = reservationCrud.countTotalReservationsByClient();
        for (int i = 0; i< report.size();i++) {
            res.add(new ClientCounter((Long) report.get(i)[1], (Client) report.get(i)[0]));
        }
        return res;
    }
}
