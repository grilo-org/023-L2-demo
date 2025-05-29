-- ***
-- SQL EXERCÍCIO 2;
-- ***

-- \/ classes com horários livres e ocupados;
SELECT
class.*,
class_schedule.*,
(DATE(class_schedule.end_time) > date('now')) as livre,
(DATE(class_schedule.end_time) <= date('now')) as ocupado
from professor p
INNER JOIN title ON title.id=p.title_id
INNER JOIN subject_prerequisite ON subject_prerequisite.id=title.prerequisite_id
INNER JOIN class_schedule ON class_schedule.id=subject_prerequisite.class_schedule_id
INNER JOIN class ON class.id=class_schedule.class_id

-- ***

-- \/ quantidade de horas de cada professor;
SELECT
p.*,
Cast ((
    JulianDay(class_schedule.end_time) - JulianDay(class_schedule.start_time)
) * 24 As Integer) as horas
from professor p
INNER JOIN title ON title.id=p.title_id
INNER JOIN subject_prerequisite ON subject_prerequisite.id=title.prerequisite_id
INNER JOIN class_schedule ON class_schedule.id=subject_prerequisite.class_schedule_id
INNER JOIN class ON class.id=class_schedule.class_id
