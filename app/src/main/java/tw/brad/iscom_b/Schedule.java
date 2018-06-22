package tw.brad.iscom_b;

import java.io.Serializable;

public class Schedule implements Serializable{
    public String ce_id, m_id, owner_id, guest, p_id, p_name, p_class,
            c_date_start, c_date_end,
            meeting_type, meeting_title, meeting_place, meeting_info,
            status;
}
