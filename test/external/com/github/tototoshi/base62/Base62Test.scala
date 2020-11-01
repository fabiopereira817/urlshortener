/*
* Copyright 2013 Toshiyuki Takahashi
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package external.com.github.tototoshi.base62

import org.scalatestplus.play.PlaySpec

class Base62Test extends PlaySpec {

  "Base62" should {

    "should encode number to base62 string" in {
      new Base62().encode(123456789L) mustBe "8M0kX"
    }

    "should decode base62 string to number" in {
      new Base62().decode("8M0kX") mustBe 123456789L
    }

  }

}
