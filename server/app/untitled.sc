import java.time.LocalTime

for (i <- 1 to 2) yield
  for (j <- 1 to 2)
     (i, j)

for (
  i <- 1 to 2;
  j <- 3 to 4
) yield (i, j)

for (
  i <- 1 to 2;
  j <- 3 to 4
) yield (i, j)


for (i <- 1 to 2) yield for (j <- 1 to 2) yield (i, j)

val a = LocalTime.of(1,1)
val b = LocalTime.of(1,2)
val c = LocalTime.of(1,2)

val total = a.plusHours(b.getHour).plusMinutes(b.getMinute)
val seq = Seq(a,b,c)

//seq.foldLeft(Seq[LocalTime]()){ (time, time1) =>
//  val hour = time1.getHour
//  val min = time1.getMinute
//  val sec = time1.getSecond
//  time
//}

seq.reduce((p,q) => p.plusHours(q.getHour))

//seq.foldLeft(Seq[LocalTime]()){(p,q) =>
//  q.plusHours(q.getHour)}

val set = Set(
  (1,2),
  (3,4)
)
val set1 = Set(
  (1,3)
)
val map1 = Map(
  1 -> 2
)
set1.subsetOf(set)
map1.toSet.subsetOf(set)