package paf.batch3.paf.assesment.batch3pafassesment.repo;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import paf.batch3.paf.assesment.batch3pafassesment.model.Occupancy;
import paf.batch3.paf.assesment.batch3pafassesment.model.Reservation;

@Repository
public class ReservationRepo {

    @Autowired
    JdbcTemplate template;

    public static final String SQL_GET_ACC = """
            select * from acc_occupancy where acc_id = ?;
                            """;

    public static final String INSERT_RESV = """
            insert into reservations(resv_id, name, email, acc_id, arrival_date, duration)
            values (?,?,?,?,?,?);
                                """;

    public static final String DEDUCT_VACANCY = """
            update acc_occupancy
            set vacancy = vacancy - ?
            where acc_id = ?;
                                    """;

    public Occupancy getVacancy(String id) {
        Occupancy occ = template.queryForObject(SQL_GET_ACC, BeanPropertyRowMapper.newInstance(Occupancy.class), id);
        return occ;
    }

    public boolean insertRes(Reservation res) {
        return template.update(INSERT_RESV, res.getResv_id(), res.getName(), res.getEmail(), res.getAcc_id(), res.getArrival(), res.getDaysOfStay()) >0;

    }

    public boolean updateVacancy(Reservation res) {
        return template.update(DEDUCT_VACANCY, res.getDaysOfStay(), res.getAcc_id())>0;
    }

}
