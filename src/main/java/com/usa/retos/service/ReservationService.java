package com.usa.retos.service;

import com.usa.retos.model.Reservation;
import com.usa.retos.reports.ClientCounter;
import com.usa.retos.reports.ReservationStatus;
import com.usa.retos.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getAll() {
        return  reservationRepository.getAll();
    }

    public Optional<Reservation> getReservation(int idReservation) {
        return reservationRepository.getReservation(idReservation);
    }

    public Reservation save (Reservation reservation) {
        if (reservation.getIdReservation() == null) {
            return reservationRepository.save(reservation);
        } else  {
            Optional<Reservation> tmpReservation = reservationRepository.getReservation(reservation.getIdReservation());
            if (tmpReservation.isEmpty()) {
                return reservationRepository.save(reservation);
            } else {
                return reservation;
            }
        }
    }

    public Reservation update (Reservation reservation) {
        if (reservation.getIdReservation() != null) {
            Optional <Reservation> tmpReservation = reservationRepository.getReservation(reservation.getIdReservation());
            if (!tmpReservation.isEmpty()) {
                return reservationRepository.save(reservation);
            }
        }
        return reservation;
    }

    public boolean deleteReservation (int id) {
        Boolean result = getReservation(id).map(reservation -> {
            reservationRepository.delete(reservation);
            return true;
        }).orElse(false);
        return result;
    }

    public ReservationStatus getReservationStatusReport() {
        List<Reservation> completed = reservationRepository.ReservationStatus("completed");
        List<Reservation> cancelled = reservationRepository.ReservationStatus("cancelled");
        return new ReservationStatus(completed.size(), cancelled.size());
    }

    public List<Reservation> getReservationTimeReport(String dateA, String dateB) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date varOne = new Date();
        Date varTwo = new Date();

        try {
            varOne = parser.parse(dateA);
            varOne = parser.parse(dateB);
        } catch (ParseException event) {
            event.printStackTrace();
        } if (varOne.before(varTwo)) {
            return reservationRepository.ReservationTime(varOne, varTwo);
        } else {
            return new ArrayList<>();
        }
    }

    public List<ClientCounter> serviceTopClients() {
        return reservationRepository.getTopClients();
    }
}
