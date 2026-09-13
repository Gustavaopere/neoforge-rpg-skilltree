# Create Quality of Life

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db814e8315d546a8641745
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Quality of Life
- **Arquivo JAR:** `Create Quality of Life-1.21.1-1.6.3-fix1.jar`
- **Versão 1.21.1:** 1.6.3-fix1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Tecnologia
- **Função:** Addon de QoL para Create que acrescenta equipamentos e utilidades próprias, incluindo ajustes de armaduras/propeller/elytra, trash-can handling e conveniências integradas ao runtime cinético do Create.
- **Dependências:** Create 6.0.10+ para a linha 1.21.1. Build NeoForge Client & Server; Chipped e JEI aparecem como integrações opcionais na distribuição oficial.
- **Sobreposição:** Complementa Create com QoL/equipamentos. Pode cruzar Create: Connected, Create Utilities J e outros addons em conveniência/interaction surfaces, mas nenhuma substituição é inferida sem comparação feature-by-feature.
- **Compatibilidade/Riscos:** Riscos em slots/armor rendering, invisibility, air-use de equipamentos, fluid voiding/bottomless supply e sobreposição com outros QoL de Create. A build 1.6.3-fix1 adiciona opção para impedir que capacetes ocultem armor durante Invisibility; validar junto do stack visual/equipamento.
- **Observações:** mod id `createqol`; runtime 1.6.3-fix1; NeoForge 1.21.1, Client & Server. 1.6.3 adicionou Trash Can blacklist, smithing de Elytra/Propeller em chestplate, config de air usage e fix de superheated lava como bottomless supply; fix1 adiciona opção de invisibility/armor para helmets.
- **Procedência:** modlist.txt física atual de 08/09/2026 (595 top-levels) + runtime `createqol` 1.6.3-fix1 + CurseForge/Modrinth oficiais da release 1.6.3-fix1 e changelog 1.6.3.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-qol
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Create Quality of Life 1.6.3-fix1, QoL components, armor/elytra/propeller, trash/fluid behavior, config e regressões confirmados no QC global #115. Runtime QA não executado.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create Quality of Life 1.6.3-fix1 foi reconfirmado como `Instalado` e reconstruído ao padrão técnico. A presença no pack não foi convertida em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🛠️ Versão física confirmada: `Create Quality of Life-1.21.1-1.6.3-fix1.jar`, mod id `createqol`, runtime `1.6.3-fix1`, NeoForge 1.21.1. É um **addon de QoL/equipamentos para Create**, não authority do sistema cinético base.

## 1. Papel e authority
Create Quality of Life adiciona conveniências e equipamentos em cima do Create. **Create continua authority de rotação, stress, contraptions, fluids e processing base**; o addon controla apenas suas próprias extensões, configs e recipes.

## 2. Equipamentos e air usage
A linha 1.6.3 integra Elytra e Propeller a chestplates por smithing e oferece configuração para tornar o consumo de ar opcional/toggleable em tools/armor suportados. Qualquer integração deve evitar cobrar ar duas vezes quando outro addon também reage ao mesmo equipamento.

## 3. Invisibility e render de armor
A build exata `1.6.3-fix1` adiciona opção para helmets não ocultarem a armadura quando o jogador recebe Invisibility. Isso é principalmente presentation/config, mas precisa coexistir com Cosmetic Armor Reworked, modelos de jogador e outros render hooks sem alterar stats server-side.

## 4. Trash Can
A 1.6.3 adiciona blacklist configurável para Trash Can. Como trash/void é operação destrutiva, filtros devem ser server-authoritative e aplicados uma única vez; automação externa não deve reenviar o mesmo stack após o addon já tê-lo consumido.

## 5. Fluids e bottomless supply
A 1.6.3 corrige superheated lava que não podia ser usada como bottomless supply fluid. O addon não redefine o sistema de fluidos do Create; ele amplia/corrige eligibility. Testar pumps/tanks e qualquer outro mod que altere infinite-fluid rules.

## 6. Create components e rendering
A mesma release corrigiu componentes Create invisíveis e a renderização de Elytra quando desabilitada por config. Resource reload, troca de equipamento e reconnect não devem deixar model state stale.

## 7. Configuração
As opções de blacklist, air usage e visibility precisam ser tratadas pelo backend real do addon. Não hardcodar defaults em scripts sem ler a config física da build atual.

## 8. Client/server
Recipes, item state, consumo de recursos e voiding são server/common. Render de armor/Elytra e feedback visual são client-side. O cliente não pode decidir sozinho se um recurso foi consumido ou um item destruído.

## 9. Integrações concretas
- **Create 6.0.10+**: dependência/authority cinética.
- **Chipped**: integração opcional publicada.
- **JEI**: integração opcional de recipe viewing; JEI não é recipe authority.
- **Cosmetic Armor Reworked**: superfície de coexistência para render de armor, sem integração oficial presumida.

## 10. Lifecycle
Validar equip/unequip, death/respawn, dimension change, reconnect, config reload/restart, resource reload e transferência de items/fluids pelas rotas do Create.

## 11. Riscos
1. Air usage cobrado duas vezes.
2. Trash Can destruir item fora do filtro configurado.
3. Bottomless-fluid rule divergir de Create.
4. Invisibility/armor hooks causarem double-render ou peça invisível indevidamente.
5. Config client-side divergir de state comum/server.
6. Outro QoL registrar recipe/interaction equivalente duas vezes.

## 12. Matriz de testes
1. Dedicated server boot + cliente 1.6.3-fix1.
2. Smithing de Elytra/Propeller e equip/unequip.
3. Air usage ligado/desligado.
4. Invisibility com opção de armor em ambos os estados.
5. Trash Can com itens permitidos e blacklisted.
6. Superheated lava em bottomless-supply scenario.
7. Resource reload e reconnect sem render stale.
8. Interação com Cosmetic Armor e outros addons Create selecionados.

## 13. Evidência
- modlist física 08/09/2026: `Create Quality of Life-1.21.1-1.6.3-fix1.jar`;
- Modrinth/CurseForge oficiais: NeoForge 1.21.1, Client & Server, Create 6.0.10+;
- changelog 1.6.3: Trash Can blacklist, Elytra/Propeller smithing, air-use config e superheated-lava fix;
- changelog 1.6.3-fix1: opção de helmet/invisibility.

> 🔒 Boundary canônico: o addon fornece **QoL próprio**; Create continua decidindo cinética, stress, contraptions e fluid mechanics base.