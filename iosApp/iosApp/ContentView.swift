import SwiftUI
import shared

struct ContentView: View {
    let greet = Greeting().greeting()
    // wrap session in a state object to make sure there is only one
    @StateObject private var model = ContentViewModel()
    
    var body: some View {
        VStack(spacing: 20) {
            Text(greet)
            HStack(spacing: 40) {
                Button("Resume") {
                    self.model.session.resume()
                }
                Button("Suspend") {
                    self.model.session.suspend()
                }
            }
            Spacer()
        }.padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
