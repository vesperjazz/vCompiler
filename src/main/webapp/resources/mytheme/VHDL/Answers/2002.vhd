library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
entity combcct is
port( A : in std_logic_vector (3 downto 0); Z: out std_logic );
end combcct;
architecture beh of combcct is
begin
process ( A )
begin
case (A) is
when "0010" => Z <='0';
when "0011" => Z <='0';
when "0101" => Z <='0';
when "0111" => Z <='0';
when "1011" => Z <='0';
when "1101" => Z <='0';
when others => Z <='1';
end case;
end process;
end beh;