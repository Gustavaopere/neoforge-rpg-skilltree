# Create Crafts & Additions

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8117a816db53640950a6
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Crafts & Additions
- **Arquivo JAR:** `createaddition-1.7.0.jar`
- **Versão 1.21.1:** 1.7.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Tecnologia, Automação, Compat
- **Função:** Ponte FE↔cinética Create com Electric Motor, Alternator, Servo Motor, Rolling Mill, wires/connectors, Accumulator, Portable Energy Interface e demais dispositivos elétricos próprios.
- **Dependências:** Pack físico: Create 6.0.10 + NeoForge 21.1.248. Linha 1.7.0 possui suporte Sable/Connector; pack contém Sable 2.0.5, Create Aeronautics 1.3.2, Sinytra Connector 2.0.0-beta.17 e Sable Create Addition Compat 0.1.13.
- **Sobreposição:** Create: New Age 1.2.0 cobre parte do domínio elétrico. Sable Create Addition Compat 0.1.13 também toca integração Sable; suporte nativo 1.7.0 não prova redundância total do bridge. Decisão Manter preservada.
- **Compatibilidade/Riscos:** 1.7.0 é Beta intencional. Riscos: loops FE↔kinetic, endpoint/cache stale, Accumulator split/merge, PEI dupe em contraption, Servo Motor lifecycle, hooks Sable duplicados com bridge 0.1.13 e overlap energético com Create: New Age. Release 1.6.0 permanece fallback estável.
- **Observações:** JAR/mod id/runtime 1.7.0 confirmados. Branch oficial `1.21.1` casa exatamente com mod 1.7.0, Create 6.0.10 e NeoForge 21.1.248. Changelog 1.7.0: novo Servo Motor + improved Sable/Connector support.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + release oficial Beta 1.7.0 + repositório oficial mrh0/createaddition branch pin-matching `1.21.1` + stack Sable/Aeronautics/Connector físico.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/createaddition
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 1.7.0 com FE↔kinetic authority, Servo Motor, electrical network/storage, moving-interface lifecycle, Sable/Connector overlap, New Age overlap e regression matrix catalogados.
- **Histórico da decisão:** 2026-09-06 — presença e versão 1.7.0 aprovadas como escolha intencional do stack Sable/Connector. Pesquisa fechada; manter fallback 1.6.0 documentado, sem alterar versão física sem nova modlist.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> ⚡ **Identidade física e source matching confirmados:** `createaddition-1.7.0.jar`, mod id `createaddition`, runtime `1.7.0`. O branch oficial `1.21.1` declara exatamente MC 1.21.1, mod 1.7.0, Create 6.0.10 e NeoForge 21.1.248 — alinhado ao pack físico.

## 1. Papel e authority
Create Crafts & Additions (CC&A) é uma ponte tecnológica entre **FE** e a cinética do Create. O addon owns suas máquinas elétricas, rede de wires/connectors, energy storage e itens próprios. Create continua owner da rede cinética/stress; NeoForge capabilities e os providers FE continuam owners do armazenamento/transferência elétrica que expõem.

## 2. Electric Motor — FE → kinetic
O Electric Motor converte energia FE em rotação Create. A conversão precisa debitar energia server-side e publicar uma fonte cinética coerente com speed/stress configurados.
Qualquer integração externa deve ler capacidade/velocidade reais, não fabricar rotação por estimativa de FE.

## 3. Alternator — kinetic → FE
O Alternator realiza a direção inversa: cinética → FE. A documentação pública descreve eficiência de conversão inferior a 100%, justamente relevante para evitar loops Motor↔Alternator com ganho líquido.
Config/runtime atual permanece authority para valores efetivos; um loop fechado deve sempre ser testado contra geração líquida indevida.

## 4. Servo Motor — novidade 1.7.0
O changelog exato da build 1.7.0 adiciona **Servo Motor**. Essa é uma superfície nova desta Beta e, portanto, regression gate principal.
Como é um componente de movimento controlado, validar energia consumida, limites de rotação, redstone/control state, stop/reverse e lifecycle de contraption sem assumir parâmetros não confirmados diretamente do runtime.

## 5. Rolling Mill
O Rolling Mill cria rotas de processamento para rods/wires e integra a cadeia elétrica ao processing Create. Recipe Manager server-side é authority para input/output.
Outros mods metalúrgicos podem registrar rods/wires equivalentes; tags/unificação precisam ser validadas para não duplicar yield ou aceitar material semanticamente incorreto.

## 6. Wires e Connectors
CC&A fornece rede própria de cabos/connectors para FE. Endpoints, capacidade e conexão são state persistente do addon; block render não é authority de conexão elétrica.
Break, move, chunk unload e dimension unload precisam invalidar caches/endpoints sem transferência fantasma.

## 7. Redstone Relay
O Redstone Relay adiciona controle lógico à infraestrutura elétrica. Redstone state e network state devem convergir no servidor; pulsos/reconnect não podem duplicar operações elétricas.

## 8. Accumulator
O **Accumulator** é armazenamento multiblock de energia. Formation/split/merge, capacidades e conteúdo precisam ser atômicos: quebrar ou reformar a estrutura não pode criar duas cópias do mesmo FE nem apagar energia sem política do próprio mod.

## 9. Portable Energy Interface
O **Portable Energy Interface** transfere energia entre mundo estacionário e contraptions Create. É uma superfície crítica de lifecycle porque cruza block entities fixas e moving contraptions.
Dock/undock, assemble/disassemble, unload e restart precisam conservar uma única quantidade de energia e uma única sessão de transferência.

## 10. Tesla Coil
A Tesla Coil usa energia para efeitos próprios, incluindo interação potencialmente danosa com entidades; versões anteriores também tiveram correções de timing/energia e apresentação.
Dano, targeting, energy debit e cooldown devem executar uma vez no servidor. Render/luz são apenas feedback client-side.

## 11. Digital Adapter e ComputerCraft
O source matching mantém integração com ComputerCraft/CC:Tweaked e documentação de peripherals. O pack físico atual **não apresenta CC:Tweaked como JAR top-level identificado**, portanto essa integração não é marcada como ativa neste pack.
A ausência atual não remove a surface upstream; apenas impede tratá-la como dependência concreta.

## 12. Combustíveis, fluidos e itens auxiliares
O projeto possui cadeia de biomass/seed oil/bioethanol, itens de combustível e utilidades para Blaze Burner, além de componentes elétricos. Recipes e fluid tags carregadas são authority para compatibilidade real.
Não presumir que todo fluido combustível de outro mod seja aceito apenas por compartilhar tag genérica.

## 13. Sable/Aeronautics — superfície concreta
A build 1.7.0 declara explicitamente **Improved Sable support, connector support**. O branch matching também foi desenvolvido com Sable/Aeronautics habilitados.
O pack físico contém Sable 2.0.5, Create Aeronautics 1.3.2 e `sable_createaddition_compat-0.1.13.jar`. Portanto existe overlap concreto entre compatibilidade nativa recente e um bridge separado.

## 14. Sable Create Addition Compat
A presença do bridge externo não prova que ele ficou obsoleto na 1.7.0. A nota upstream “improved Sable support” é insuficiente para afirmar cobertura total dos patches do bridge.
Risco: hooks duplicados de docking/connector/energy transfer. Necessidade do bridge deve ser decidida em auditoria própria com source/JAR matching e teste A/B, não nesta página.

## 15. Sinytra Connector
O pack contém Sinytra Connector 2.0.0-beta.17+1.21.1. O changelog 1.7.0 cita melhoria de `connector support`, mas não detalha publicamente cada code path.
Registrar como superfície de compatibilidade relevante sem inferir que o mod passou a depender do Connector ou que toda adaptação Fabric↔NeoForge é coberta.

## 16. Create: New Age — overlap tecnológico
O pack também contém Create: New Age 1.2.0, que possui suas próprias superfícies elétricas. Há overlap de domínio em geração/uso/transmissão de energia, mas não se presume equivalência máquina-a-máquina.
A decisão formal **Manter** de CC&A permanece preservada; balanceamento deve comparar conversões, storage, cabos e progression reais antes de remover qualquer sistema.

## 17. Build status e fallback
A 1.7.0 é publicada como **Beta** em 16/08/2026. A 1.6.0 permanece a Release estável anterior.
A escolha 1.7.0 é intencional no pack por Servo Motor + suporte Sable/Connector; fallback 1.6.0 permanece documentado sem autorizar downgrade automático.

## 18. Client/server e multiplayer
FE amount, kinetic state, energy transfer, multiblock storage e damage/effects são server-authoritative. Models, wires, overlays e animações são client-facing.
Com múltiplos clientes, nenhum endpoint deve ser conectado duas vezes ou transferir energia por sessão fantasma.

## 19. Data/config e reload
Recipes/configs controlam conversões, processing e compatibilidade. `/reload` não pode trocar recipe em andamento de forma a duplicar output. Mudanças de config elétrica devem reconstruir state/caches de maneira segura quando necessário.

## 20. Lifecycle crítico
Testar place/break de endpoints, chunk unload, restart, assemble/disassemble de contraptions, PEI docking, accumulator split/merge, Servo Motor em movimento e perda súbita de FE.
Todo persistent state deve convergir para uma única quantidade de energia/posição após reload.

## 21. Riscos
1. Loop Motor↔Alternator produz FE ou rotação líquida indevida.
2. Servo Motor 1.7.0 duplica movimento/state após stop/restart.
3. Wire endpoint/cache persiste após break/unload.
4. Accumulator split/merge duplica ou apaga FE.
5. Portable Energy Interface duplica energia em dock/undock.
6. Sable native support + bridge 0.1.13 aplicam hook duplicado.
7. Connector support da Beta diverge do stack físico atual.
8. Tesla Coil aplica dano/efeito duas vezes ou sem debit correto.
9. Recipes Rolling Mill criam yield econômico excessivo com outros metal mods.
10. Create New Age oferece loop de conversão cruzada não balanceado.
11. Beta 1.7.0 introduz regressão não presente na Release 1.6.0.
12. Integração CC:Tweaked é presumida ativa apesar de ausência física atual.

## 22. Matriz de testes
- [ ] Dedicated server inicia com CC&A 1.7.0 + Create 6.0.10 + NeoForge 21.1.248.
- [ ] Electric Motor debita FE e gera cinética uma única vez.
- [ ] Alternator gera FE sem criar ganho líquido em loop com Motor.
- [ ] Servo Motor executa start/stop/reverse e limites reais sem state fantasma.
- [ ] Rolling Mill processa recipes exatamente uma vez.
- [ ] Wires/connectors invalidam endpoints ao quebrar/unloadar chunks.
- [ ] Accumulator preserva FE em formation/split/restart.
- [ ] Portable Energy Interface conserva energia em dock/undock de contraption.
- [ ] Sable 2.0.5 + Aeronautics 1.3.2 funcionam com o suporte 1.7.0.
- [ ] Bridge `sable_createaddition_compat` 0.1.13 não causa hook/transferência duplicada.
- [ ] Sinytra Connector presente não causa crash/classloading indevido.
- [ ] Tesla Coil debita energia e aplica efeito uma vez.
- [ ] Interação com Create: New Age não cria conversão energética positiva em ciclo.
- [ ] Restart/chunk unload preserva network/FE state.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 23. Evidências e limites
A modlist física confirma JAR/runtime 1.7.0, NeoForge 21.1.248 e o stack Sable/Aeronautics/Connector. A publicação oficial confirma que 1.7.0 é Beta, adiciona Servo Motor e melhora suporte Sable/Connector. O branch oficial `1.21.1` é pin-matching com mod 1.7.0, Create 6.0.10 e NeoForge 21.1.248. A documentação pública confirma as principais máquinas/superfícies; parâmetros não pinados por config/source específico permanecem sob authority do runtime.

> 🔒 **Boundary canônico:** CC&A owns a ponte FE↔kinetic e sua rede elétrica; Create owns kinetics; providers FE own suas capabilities. A decisão formal **Manter** e o fallback 1.6.0 são preservados; suporte Sable nativo não torna automaticamente o bridge externo redundante.
