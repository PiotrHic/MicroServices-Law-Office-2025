import ClientList from "../components/ClientList";
import ClientForm from "../components/ClientForm";

export default function ClientsPage() {
    return (
        <div>
            <h1>Klienci</h1>
            <ClientForm onAdded={() => window.location.reload()} />
            <ClientList />
        </div>
    );
}