package urlshortener.implementations

import java.time.{Clock, Instant, ZoneId}

class MockClock(dateInEpochMillis: Long) extends Clock {

    private var myMillis = dateInEpochMillis

    override def getZone: ZoneId = ZoneId.of("UTF-8")

    override def withZone(zone: ZoneId): Clock = this

    override def instant(): Instant = {
      val epoch = Instant.ofEpochMilli(myMillis)
      myMillis = myMillis + 1
      epoch
    }

}
